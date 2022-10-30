package com.genesis.exception;

public class ServerRequestException extends WeaponException {

  public ServerRequestException(String message) {
    super(message);
  }

  public ServerRequestException(String message, Throwable cause) {
    super(message, cause);
  }
}
