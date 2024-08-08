package br.com.erudio.securityJwt;

import br.com.erudio.data.vo.v1.security.TokenVO;
import br.com.erudio.exceptions.InvalidJwtAuthenticationException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
public class JwtTokenProvider {
  /**
   * If a value is not provided for the 'security.jwt.token.secret-key'
   * property, the default value 'secret' is used.
   */
  @Value("${security.jwt.token.secret-key: secret}")
  private String secretKey = "secret";

  @Value("${security.jwt.token.expire-length: 3600000}")
  private long validityInMilliseconds = 3600000;

  @Autowired
  private UserDetailsService userDetailsService;

  /**
   * This field here represents the hashing algorithm that
   * will be used to create a jwt token based on user data
   * and the secret key.
   */
  Algorithm algorithm = null;

  /**
   * The `init` method is annotated with `@PostConstruct`, which means it will be
   * executed automatically by the Spring framework after the bean's properties
   * have been initialized. This method performs the following tasks:
   * <p>
   * 1. Encodes the `secretKey` field in Base64 format.
   * 2. Initializes the `algorithm` field with the HMAC256 algorithm using the
   * encoded `secretKey`.
   * <p>
   * If the PostConstruct annotation is not used, the `init` method would be executed
   * only when manually called by the developer.
   * <p>
   * If this method is not executed, the `secretKey` will not be encoded, and the
   * `algorithm` field will not be initialized, which will cause any JWT token
   * creation or validation operations to fail.
   */
  @PostConstruct
  protected void init() {
    secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    algorithm = Algorithm.HMAC256(secretKey.getBytes());
  }

  public TokenVO createAccessToken(String username, List<String> roles) {
    Date now = new Date();
    Date validity = new Date(now.getTime() + validityInMilliseconds);

    var accessToken = getAccessToken(username, roles, now, validity);
    var refreshToken = getRefreshToken(username, roles, now);

    return new TokenVO(username, true, now, validity, accessToken, refreshToken);
  }

  /**
   * The `getAccessToken` method creates a JWT access token based on the provided username,
   * roles, current date, and expiration date. It includes the following information in the token:
   * <p>
   * 1. `roles`: The user's roles.<br>
   * 2. `issuedAt`: The date and time the token was issued.<br>
   * 3. `expiresAt`: The date and time the token expires.<br>
   * 4. `subject`: The username.<br>
   * 5. `issuer`: The token issuer's URL.
   * <p>
   * The token is signed using the HMAC256 algorithm and the encoded secret key.
   */
  private String getAccessToken(String username, List<String> roles, Date now, Date vality) {
    String issueUrl = ServletUriComponentsBuilder
        .fromCurrentContextPath()
        .build()
        .toUriString();

    return JWT.create()
        .withClaim("roles", roles)
        .withIssuedAt(now)
        .withExpiresAt(vality)
        .withSubject(username)
        .withIssuer(issueUrl)
        .sign(algorithm)
        .strip();
  }

  /**
   * The `getRefreshToken` method creates a JWT refresh token based on the provided username,
   * roles, and current date. It includes the following information in the token:
   * <p>
   * 1. `roles`: The user's roles.<br>
   * 2. `issuedAt`: The date and time the token was issued.<br>
   * 3. `expiresAt`: The date and time the token expires (three times the access token's validity period).<br>
   * 4. `subject`: The username.
   * <p>
   * The token is signed using the HMAC256 algorithm and the encoded secret key.
   */
  private String getRefreshToken(String username, List<String> roles, Date now) {
    // The refresh token is valid for 3 hours.
    Date validityRefreshToken = new Date(now.getTime() + (validityInMilliseconds * 3));

    return JWT.create()
        .withClaim("roles", roles)
        .withIssuedAt(now)
        .withExpiresAt(validityRefreshToken)
        .withSubject(username)
        .sign(algorithm)
        .strip();
  }

  /**
   * The `getAuthentication` method decodes the provided JWT token and loads the user details
   * based on the username contained in the token. It returns an `Authentication` object that
   * contains the user details and their authorities.
   */
  public Authentication getAuthentication(String token) {
    DecodedJWT decodedJWT = decodeToken(token);
    UserDetails userDetails = userDetailsService
        .loadUserByUsername(decodedJWT.getSubject());

    return new UsernamePasswordAuthenticationToken(
        userDetails,
        "",
        userDetails.getAuthorities()
    );
  }

  /**
   * The `decodeToken` method decodes the provided JWT token using the HMAC256 algorithm
   * and the encoded secret key. It returns a `DecodedJWT` object that contains the
   * decoded information from the token.
   */
  private DecodedJWT decodeToken(String token) {
    Algorithm alg = Algorithm.HMAC256(secretKey.getBytes());
    JWTVerifier verifier = JWT.require(alg).build();

    DecodedJWT decodedJWT = verifier.verify(token);
    return decodedJWT;
  }

  /**
   * The `resolveToken` method extracts the JWT token from the HTTP request's authorization header.
   * If the header starts with "Bearer ", it returns the token without the "Bearer " prefix.
   * Otherwise, it returns null.
   */
  public String resolveToken(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");

    if (bearerToken != null && bearerToken.startsWith("Bearer "))
      return bearerToken.substring(7);

    return null;
  }

  /**
   * The `validateToken` method validates the provided JWT token. It decodes the token and checks
   * if the expiration date is before the current date. If the token is expired or invalid,
   * it throws an `InvalidJwtAuthenticationException`.
   */
  public boolean validateToken(String token) throws InvalidJwtAuthenticationException {
    DecodedJWT decodedJWT = decodeToken(token);

    try {
      if (decodedJWT.getExpiresAt().before(new Date()))
        return false;
      return true;
    } catch (Exception e) {
      throw new InvalidJwtAuthenticationException("Expired or invalid token");
    }
  }
}
