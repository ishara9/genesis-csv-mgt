package com.genesis.dto;

import java.util.List;
import lombok.Data;

@Data
public abstract class PaginatedResponseDto<D> {

  private int size;
  private long totalElements;
  private int totalPages;
  private List<D> content;
  private int currentPage;

}
