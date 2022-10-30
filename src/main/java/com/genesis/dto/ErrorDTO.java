package com.genesis.dto;

import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class ErrorDTO {

  private final String message;
  private final ZonedDateTime zonedDateTime;

}
