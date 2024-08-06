package br.com.erudio.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.naming.AuthenticationException;

/**
 * Using the ResponseStatus annotation, we can define a fixed HTTP
 * status code that will be returned when this exception is thrown.
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class InvalidJwtAuthenticationException extends AuthenticationException {
  public InvalidJwtAuthenticationException(String ex) {
    super(ex);
  }
}
