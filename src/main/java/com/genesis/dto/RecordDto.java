package com.genesis.dto;

import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecordDto {

    private String code;
    private String source;
    private String displayValue;
    private String longDescription;
    private Date fromDate;
    private Date toDate;
    private Long sortingPriority;
}
