package io.lindx.server.security.error;

public class UserNotFoundExeption extends RuntimeException {
  public UserNotFoundExeption(String message) {
    super(message);
  }
}
