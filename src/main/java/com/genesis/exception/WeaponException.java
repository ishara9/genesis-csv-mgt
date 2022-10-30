package com.genesis.exception;

public class WeaponException extends RuntimeException {

  public WeaponException() {
  }

  public WeaponException(String message) {
    super(message);
  }

  public WeaponException(String message, Throwable cause) {
    super(message, cause);
  }


}
