package br.com.erudio.services;

import br.com.erudio.data.vo.v1.security.AccountCredentialsVO;
import br.com.erudio.data.vo.v1.security.TokenVO;
import br.com.erudio.repositories.UserRepository;
import br.com.erudio.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthServices {
  /**
   * The AuthenticationManager class is a core component of Spring Security that is used to process
   * authentication requests. It is responsible for authenticating the user based on their credentials
   * (username and password) and returning an Authentication object if the authentication is successful.
   * This field is used to authenticate the user during the sign-in process.
   */
  @Autowired
  private AuthenticationManager authenticationManager;

  /**
   * The JwtTokenProvider class is responsible for generating and validating JWT tokens. It provides
   * methods to create access tokens and refresh tokens, as well as to validate and parse these tokens.
   * This field is used to generate a new access token for the authenticated user.
   */
  @Autowired
  private JwtTokenProvider tokenProvider;

  /**
   * The UserRepository interface extends JpaRepository and provides methods to interact with the
   * database for User entities. It includes methods to find users by their username. This field is
   * used to retrieve the user details from the database during the sign-in process.
   */
  @Autowired
  private UserRepository repository;

  /**
   * This method handles the sign-in process for the user. It takes the user's credentials (username
   * and password) as input, authenticates the user, and generates an access token if the authentication
   * is successful. The access token is then returned in the response.
   *
   * @param data The user's credentials (username and password) encapsulated in an AccountCredentialsVO object.
   * @return A ResponseEntity containing the generated access token if the authentication is successful.
   */
  @SuppressWarnings("rawtypes")
  public ResponseEntity signin(AccountCredentialsVO data) {
    try {
      var username = data.getUsername();
      var password = data.getPassword();

      /**
       * The authenticate method of the AuthenticationManager is used to authenticate the user with the
       * provided credentials. It takes a UsernamePasswordAuthenticationToken instance as a parameter,
       * which encapsulates the username and password for authentication. If the authentication is
       * successful, an Authentication object is returned.
       */
      authenticationManager
          .authenticate(new UsernamePasswordAuthenticationToken(username, password));

      var user = repository.findByUsername(username);
      var tokenResponse = new TokenVO();

      if (user != null) {
        /**
         * The createAccessToken method of the JwtTokenProvider is used to generate a new access token
         * for the authenticated user. It takes the username and user roles as parameters to include
         * them in the token. The generated token is then encapsulated in a TokenVO object and returned
         * in the response.
         */
        tokenResponse = tokenProvider.createAccessToken(username, user.getRoles());
      } else {
        throw new UsernameNotFoundException("Username " + username + " not found");
      }

      return ResponseEntity.ok(tokenResponse);
    } catch (Exception e) {
      /**
       * If any exception occurs during the authentication process, a BadCredentialsException is thrown
       * with a message indicating that the username or password supplied is invalid. This exception is
       * caught and handled by the Spring Security framework to return an appropriate error response.
       */
      throw new BadCredentialsException("Invalid username/password supplied");
    }
  }
}