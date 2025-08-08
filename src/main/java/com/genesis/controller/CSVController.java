package com.genesis.controller;

import com.genesis.dto.PaginatedRecordsDto;
import com.genesis.dto.RecordDto;
import com.genesis.service.CSVService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RequestMapping(path = "api/v1/csv")
@RestController
@Slf4j
@AllArgsConstructor
@Tag(name = "CSV Management", description = "Endpoints for managing CSV records")
public class CSVController {

  private final CSVService csvService;

  @PostMapping("/sec")
  public ResponseEntity<Void> postSecure(@RequestBody Object nothing) {
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @Operation(summary = "Upload a file")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "File uploaded successfully")
  })
  @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<String> upload(
          @Parameter(description = "File to upload", required = true,
                  content = @Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE,
                          schema = @Schema(type = "string", format = "binary")))
          @RequestPart("file") MultipartFile file) {

    csvService.upload(file);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }


  @Operation(summary = "Get a record by ID", description = "Retrieves a single record by its unique ID.")
  @GetMapping(path = "records/{recordId}")
  ResponseEntity<RecordDto> getRecordById(@PathVariable String recordId) {
    RecordDto record = csvService.getRecordById(recordId);
    return new ResponseEntity<>(record, HttpStatus.OK);
  }

  @Operation(summary = "Get all records", description = "Retrieves paginated records from the CSV file.")
  @GetMapping(path = "records")
  ResponseEntity<PaginatedRecordsDto<RecordDto>> getAllRecords(
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

  @Operation(summary = "Delete a record by ID", description = "Deletes a single record by its unique ID.")
  @DeleteMapping(path = "records/{recordId}")
  ResponseEntity<Void> deleteRecordById(@PathVariable String recordId) {
    csvService.deleteRecordById(recordId);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @Operation(summary = "Delete all records", description = "Deletes all records from the CSV file.")
  @DeleteMapping(path = "records")
  ResponseEntity<Void> deleteAllRecords() {
    csvService.deleteAllRecords();
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
