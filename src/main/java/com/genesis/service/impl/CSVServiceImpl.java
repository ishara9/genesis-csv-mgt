package com.genesis.service.impl;

import com.genesis.dto.RecordDto;
import com.genesis.exception.ClientRequestException;
import com.genesis.model.Record;
import com.genesis.repository.CSVRepository;
import com.genesis.service.CSVService;
import com.genesis.util.RecordMapper;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
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

    List<Record> recordList = StreamSupport.stream(records.spliterator(), false)
        .map(RecordMapper::map)
        .collect(Collectors.toList());
    csvRepository.saveAll(recordList);

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
