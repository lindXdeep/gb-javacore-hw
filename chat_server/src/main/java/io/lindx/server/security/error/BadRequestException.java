package io.lindx.server.security.error;

public class BadRequestException extends RuntimeException {

  public BadRequestException(String message) {
    super(message);
  }
  
}
