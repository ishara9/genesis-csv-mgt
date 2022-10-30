package com.genesis.exception;

public class GenesisException extends RuntimeException {

  public GenesisException() {
  }

  public GenesisException(String message) {
    super(message);
  }

  public GenesisException(String message, Throwable cause) {
    super(message, cause);
  }


}
