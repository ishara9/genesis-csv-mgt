package com.genesis.util;


import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class CustomPagealbe implements Pageable {


  private int limit;
  private int offset;
  private final Sort sort;

  public CustomPagealbe(int offset, int limit) {
    this(offset, limit, Sort.unsorted());
  }


  public CustomPagealbe(int offset, int limit, Sort sort) {
    this.limit = limit;
    this.offset = offset;
    this.sort = sort;
  }


  @Override
  public int getPageNumber() {
    return offset / limit;
  }

  @Override
  public int getPageSize() {
    return limit;
  }

  @Override
  public long getOffset() {
    return offset;
  }

  @Override
  public Sort getSort() {
    return this.sort;
  }

  @Override
  public Pageable next() {
    return new CustomPagealbe((int) getOffset() + getPageSize(), getPageSize(), getSort());
  }

  @Override
  public Pageable previousOrFirst() {
    return hasPrevious() ? previous() : first();
  }

  @Override
  public Pageable first() {
    return new CustomPagealbe(0, getPageSize(), getSort());
  }

  public Pageable previous() {
    throw new UnsupportedOperationException("not supported");
  }

  @Override
  public Pageable withPage(int pageNumber) {
    return PageRequest.of(pageNumber, limit);
  }

  @Override
  public boolean hasPrevious() {
    return offset > limit;
  }
}
