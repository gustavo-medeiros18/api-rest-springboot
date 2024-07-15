package br.com.erudio.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Using the ResponseStatus annotation, we can define a fixed HTTP
 * status code that will be returned when this exception is thrown.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UnsupportedMathOperationException extends RuntimeException {
  public UnsupportedMathOperationException(String ex) {
    super(ex);
  }
}
