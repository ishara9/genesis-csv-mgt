package com.genesis.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ClientRequestException extends GenesisException {

  public ClientRequestException(String message) {
    super(message);
  }

  public ClientRequestException(String message, Throwable throwable) {
    super(message, throwable);
  }
}
