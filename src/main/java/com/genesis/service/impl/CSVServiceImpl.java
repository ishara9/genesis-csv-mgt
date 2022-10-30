package com.genesis.service.impl;

import com.genesis.dto.RecordDto;
import com.genesis.exception.ClientRequestException;
import com.genesis.repository.CSVRepository;
import com.genesis.service.CSVService;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@AllArgsConstructor
public class CSVServiceImpl implements CSVService {

  private final CSVRepository csvRepository;
  private final ModelMapper modelMapper;

  @Override
  public void upload(MultipartFile file) {


    Reader reader = null;
    Iterable<CSVRecord> records;
    try {
      reader = new InputStreamReader(file.getInputStream());
      records = CSVFormat.DEFAULT
          .withFirstRecordAsHeader()
          .parse(reader);
    } catch (IOException e) {
      throw new ClientRequestException(e.getMessage(), e);
    }



    for (CSVRecord record : records) {
      String source = record.get("source");
      String code = record.get("code");
    }

  }

  @Override
  public RecordDto getRecordById(String recordId) {
    return null;
  }

  @Override
  public List<RecordDto> getAllRecords(int limit, int offset) {
    return null;
  }

  @Override
  public void deleteRecordById(String id) {

  }

  @Override
  public void deleteAllRecords() {

  }
}
