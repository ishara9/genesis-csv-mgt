package com.genesis.controller;

import com.genesis.dto.RecordDto;
import com.genesis.service.CSVService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping(path = "api/v1/csv")
@RestController
@Slf4j
@AllArgsConstructor
public class CSVController {

  private final CSVService csvServiceUtil;

  @PostMapping()
  public ResponseEntity<Void> uploadFile(@RequestParam("file") MultipartFile file) {
    csvServiceUtil.upload(file);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @GetMapping(path = "records/{recordId}")
    ResponseEntity<RecordDto> getRecordById(@PathVariable String recordId) {
    RecordDto record = csvServiceUtil.getRecordById(recordId);
    return new ResponseEntity<>(record, HttpStatus.OK);
  }

  @GetMapping(path = "records")
  ResponseEntity<List<RecordDto>> getAllRecords(
          @RequestParam(value = "limit", required = false) Integer limit,
          @RequestParam(value = "offset", required = false) Integer offset) {
    // Sanitize pagination params.
    if (limit != null) {
      limit = (20 < limit) || (limit < 1) ? 20 : limit;
    } else {
      limit = 20;
    }
    if (offset != null) {
      offset = 0 > offset ? 0 : offset;
    } else {
      offset = 0;
    }

    List<RecordDto> records = csvServiceUtil.getAllRecords(limit, offset);
    return new ResponseEntity<>(records, HttpStatus.OK);
  }

  @DeleteMapping(path = "records/{recordId}")
  ResponseEntity<Void> deleteRecordById(@PathVariable String recordId) {
    csvServiceUtil.deleteRecordById(recordId);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @DeleteMapping(path = "records")
  ResponseEntity<Void> deleteAllRecords() {
    csvServiceUtil.deleteAllRecords();
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
