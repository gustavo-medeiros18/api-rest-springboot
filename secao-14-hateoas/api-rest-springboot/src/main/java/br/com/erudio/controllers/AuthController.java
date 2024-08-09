package br.com.erudio.controllers;

import br.com.erudio.data.vo.v1.security.AccountCredentialsVO;
import br.com.erudio.services.AuthServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
   *         or a ResponseEntity with a status of FORBIDDEN if the credentials are invalid.
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

  private static boolean checkIfParamsIsNotNull(AccountCredentialsVO data) {
    return data == null ||
        data.getUsername() == null ||
        data.getUsername().isBlank() ||
        data.getPassword() == null ||
        data.getPassword().isBlank();
  }
}
