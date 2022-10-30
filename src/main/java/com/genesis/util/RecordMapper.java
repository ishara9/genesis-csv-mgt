package com.genesis.util;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.apache.commons.csv.CSVRecord;
import com.genesis.model.Record;

public class RecordMapper {

  public static Record map(CSVRecord csvRecord) {
    SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
    try {
      Record record = Record.builder()
          .code(csvRecord.get("code"))
          .codeListCode(csvRecord.get("codeListCode"))
          .source(csvRecord.get("source"))
          .displayValue(csvRecord.get("displayValue"))
          .longDescription(csvRecord.get("longDescription"))
          .fromDate(new Date(sdf1.parse(csvRecord.get("fromDate")).getTime()))
          .build();
      if(!csvRecord.get("toDate").isEmpty()){
        record.setToDate(new Date(sdf1.parse(csvRecord.get("toDate")).getTime()));
      }
      if(!csvRecord.get("sortingPriority").isEmpty()) {
        record.setSortingPriority(Long.parseLong(csvRecord.get("sortingPriority")));
      }
      return record;
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
  }
}
