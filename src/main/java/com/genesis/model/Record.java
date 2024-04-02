package com.genesis.model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "RECORD")
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Data
@Builder
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

  @Column(name = "CODE_LIST_CODE")
  private String codeListCode;

}
