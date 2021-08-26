package io.lindx.server.security.error;

public class WrongCredentialsException extends RuntimeException {
  public WrongCredentialsException(String message) {
    super(message);
  }
}