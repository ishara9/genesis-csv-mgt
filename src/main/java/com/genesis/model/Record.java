package com.genesis.model;

import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "RECORD")
@AllArgsConstructor
@ToString
public class Record {

  @Id
  @Column(name = "CODE", nullable = false)
  private String code;

  @Column(name = "SOURCE", nullable = false)
  private String source;

  @Column(name = "DISPLAY_VALUE", nullable = false)
  private String displayValue;

  @Column(name = "LONG_DESCRIPTION")
  private String longDescription;

  @Column(name = "FROM_DATE", nullable = false)
  private Date fromDate;

  @Column(name = "TO_DATE")
  private Date toDate;

  @Column(name = "SORTING_PRIORITY")
  private Long sortingPriority;

}
