package br.com.erudio.controllers;

import br.com.erudio.data.vo.v1.security.AccountCredentialsVO;
import br.com.erudio.services.AuthServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Authentication endpoint")
@RestController
@RequestMapping("/auth")
public class AuthController {
  @Autowired
  AuthServices authServices;

  /**
   * This method handles the user authentication process. It receives the user's credentials
   * (username and password) encapsulated in an AccountCredentialsVO object, validates them,
   * and if valid, delegates the authentication process to the AuthServices class.
   * <p>
   * If the authentication is successful, it returns a ResponseEntity containing the generated token.
   * <p>
   * If the credentials are invalid or the authentication fails, it returns a ResponseEntity
   * with a status of FORBIDDEN and an appropriate error message.
   *
   * @param data The user's credentials (username and password) encapsulated in an AccountCredentialsVO object.
   * @return A ResponseEntity containing the generated token if the authentication is successful,
   * or a ResponseEntity with a status of FORBIDDEN if the credentials are invalid.
   */
  @SuppressWarnings("rawtypes")
  @Operation(summary = "Authenticates a user and returns a token")
  @PostMapping(value = "/signin")
  public ResponseEntity signin(@RequestBody AccountCredentialsVO data) {
    if (checkIfParamsIsNotNull(data))
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid credentials");

    var token = authServices.signin(data);
    if (token == null)
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid credentials");

    return token;
  }

  /**
   * This method handles the process of refreshing the token for the authenticated user. It receives
   * the username and the refresh token as input, validates the user, and generates a new access token
   * if the user is found. The new access token is then returned in the response.
   *
   * @param username     The username of the authenticated user.
   * @param refreshToken The refresh token provided by the user.
   * @return A ResponseEntity containing the new access token if the refresh is successful.
   */
  @SuppressWarnings("rawtypes")
  @Operation(summary = "Refresh token for authenticated user and returns a token")
  @PutMapping(value = "/refresh/{username}")
  public ResponseEntity refreshToken(
      @PathVariable("username") String username,
      @RequestHeader("Authorization") String refreshToken
  ) {
    if (checkIfParamsIsNotNull(username, refreshToken))
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid credentials");

    var token = authServices.refreshToken(username, refreshToken);
    if (token == null)
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid credentials");

    return token;
  }

  private static boolean checkIfParamsIsNotNull(String username, String refreshToken) {
    return refreshToken == null ||
        refreshToken.isBlank() ||
        username == null ||
        username.isBlank();
  }

  private static boolean checkIfParamsIsNotNull(AccountCredentialsVO data) {
    return data == null ||
        data.getUsername() == null ||
        data.getUsername().isBlank() ||
        data.getPassword() == null ||
        data.getPassword().isBlank();
  }
}
