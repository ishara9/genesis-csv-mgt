package com.genesis.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.domain.Page;

@EqualsAndHashCode(callSuper = true)
@Data
public class PaginatedRecordsDto<D> extends PaginatedResponseDto<D> {

  public PaginatedRecordsDto(Page<D> pageResult) {
    this.setSize(pageResult.getSize());
    this.setTotalElements(pageResult.getTotalElements());
    this.setTotalPages(pageResult.getTotalPages());
    this.setCurrentPage(pageResult.getPageable().getPageNumber());
    this.setContent(pageResult.getContent());
  }

}
