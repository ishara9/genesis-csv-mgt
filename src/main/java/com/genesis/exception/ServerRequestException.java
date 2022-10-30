package com.genesis.exception;

public class ServerRequestException extends GenesisException {

  public ServerRequestException(String message) {
    super(message);
  }

  public ServerRequestException(String message, Throwable cause) {
    super(message, cause);
  }
}
