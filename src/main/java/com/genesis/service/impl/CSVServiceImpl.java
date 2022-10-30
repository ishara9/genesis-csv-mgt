package com.genesis.service.impl;

import com.genesis.dto.RecordDto;
import com.genesis.exception.ClientRequestException;
import com.genesis.exception.ServerRequestException;
import com.genesis.model.Record;
import com.genesis.repository.CSVRepository;
import com.genesis.service.CSVService;
import com.genesis.util.RecordMapper;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.dao.DataAccessException;
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
    try {
      csvRepository.saveAll(recordList);
    } catch (DataAccessException dEx) {
      throw new ServerRequestException("Error while saving data", dEx);
    }
  }

  @Override
  public RecordDto getRecordById(String recordId) {

    Optional<Record> optionalRecord = csvRepository.findById(recordId);
    if (optionalRecord.isPresent()) {
      Record record = optionalRecord.get();
      return modelMapper.map(record, RecordDto.class);
    }
    throw new ClientRequestException("Invalid recordId");
  }

  @Override
  public List<RecordDto> getAllRecords(int limit, int offset) {
    //TODO implement pageable logic
    List<Record> records = csvRepository.findAll();
    return modelMapper.map(records, new TypeToken<List<RecordDto>>() {
    }.getType());
  }

  @Override
  public void deleteRecordById(String id) {
    boolean isValidId = csvRepository.existsById(id);
    if (!isValidId) {
      throw new ClientRequestException("Invalid record id.");
    }
    try {
      csvRepository.deleteById(id);
    } catch (DataAccessException dEx) {
      throw new ServerRequestException("Error while deleting data", dEx);
    }
  }

  @Override
  public void deleteAllRecords() {
    try {
      csvRepository.deleteAll();
    } catch (DataAccessException dEx) {
      throw new ServerRequestException("Error while deleting data", dEx);
    }
  }
}
