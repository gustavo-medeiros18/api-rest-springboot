package br.com.erudio.securityJwt;

import br.com.erudio.data.vo.v1.security.TokenVO;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

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
    algorithm = Algorithm.HMAC256(secretKey);
  }

  public TokenVO createAccessToken(String username, List<String> roles) {
    Date now = new Date();
    Date validity = new Date(now.getTime() + validityInMilliseconds);

    var accessToken = getAccessToken(username, roles, now, validity);
    var refreshToken = getAccessToken(username, roles, now);

    return new TokenVO(username, true, now, validity, accessToken, refreshToken);
  }

  private String getAccessToken(String username, List<String> roles, Date now) {
    return "";
  }

  private String getAccessToken(String username, List<String> roles, Date now, Date vality) {
    return "";
  }
}
