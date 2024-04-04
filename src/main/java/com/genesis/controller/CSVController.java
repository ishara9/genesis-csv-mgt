package com.genesis.controller;

import com.genesis.dto.PaginatedRecordsDto;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping(path = "api/v1/csv")
@RestController
@Slf4j
@AllArgsConstructor
public class CSVController {

  private final CSVService csvService;

  @PostMapping("/sec")
  public ResponseEntity<Void> postSecure(@RequestBody Object nothing) {
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @PostMapping()
  public ResponseEntity<Void> uploadFile(@RequestParam("file") MultipartFile file) {
    csvService.upload(file);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @GetMapping(path = "records/{recordId}")
  ResponseEntity<RecordDto> getRecordById(@PathVariable String recordId) {
    RecordDto record = csvService.getRecordById(recordId);
    return new ResponseEntity<>(record, HttpStatus.OK);
  }

  @GetMapping(path = "records")
  ResponseEntity<PaginatedRecordsDto> getAllRecords(
      @RequestParam(value = "limit", required = false) Integer limit,
      @RequestParam(value = "offset", required = false) Integer offset) {
    // Sanitize pagination params.
    if (limit != null) {
      limit = (limit < 1) || (20 < limit) ? 20 : limit;
    } else {
      limit = 20;
    }
    if (offset != null) {
      offset = offset < 0 ? 0 : offset;
    } else {
      offset = 0;
    }

    PaginatedRecordsDto<RecordDto> records = csvService.getAllRecords(limit, offset);
    return new ResponseEntity<>(records, HttpStatus.OK);
  }

  @DeleteMapping(path = "records/{recordId}")
  ResponseEntity<Void> deleteRecordById(@PathVariable String recordId) {
    csvService.deleteRecordById(recordId);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @DeleteMapping(path = "records")
  ResponseEntity<Void> deleteAllRecords() {
    csvService.deleteAllRecords();
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
