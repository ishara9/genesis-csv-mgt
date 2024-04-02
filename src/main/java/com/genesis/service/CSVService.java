package com.genesis.service;

import com.genesis.dto.PaginatedRecordsDto;
import com.genesis.dto.RecordDto;
import org.springframework.web.multipart.MultipartFile;

/**
 * This interface defines methods to handle CSV records.
 */
public interface CSVService {

  /**
   * Upload csv file.
   *
   * @param file CSV file.
   */
  void upload(MultipartFile file);

  /**
   * Fetch single record by id.
   *
   * @param recordId Unique record code.
   * @return
   */
  RecordDto getRecordById(String recordId);

  /**
   * Get all records with pagination.
   *
   * @param limit  Records to return.
   * @param offset Records to skip.
   * @return paginated records dto
   */
  PaginatedRecordsDto<RecordDto> getAllRecords(int limit, int offset);

  /**
   * Delete single record by id.
   *
   * @param id Unique record code.
   */
  void deleteRecordById(String id);

  /**
   * Delete all records.
   */
  void deleteAllRecords();
}
