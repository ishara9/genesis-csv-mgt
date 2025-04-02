package com.genesis.service.impl;

import com.genesis.dto.PaginatedRecordsDto;
import com.genesis.dto.RecordDto;
import com.genesis.exception.ClientRequestException;
import com.genesis.exception.ServerRequestException;
import com.genesis.model.Record;
import com.genesis.repository.CSVRepository;
import com.genesis.service.CSVService;
import com.genesis.util.CustomPagealbe;
import com.genesis.util.RecordMapper;
import com.sun.source.tree.Tree;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
public class CSVServiceImpl implements CSVService {

  private final CSVRepository csvRepository;
  private final ModelMapper modelMapper;

  public CSVServiceImpl(CSVRepository csvRepository, ModelMapper modelMapper) {
    this.csvRepository = csvRepository;
    this.modelMapper = modelMapper;
  }

  @Override
  public void upload(MultipartFile file) {

    Reader reader = null;
    Iterable<CSVRecord> records;
    try {
      reader = new InputStreamReader(file.getInputStream());
      records = CSVFormat.Builder.create(CSVFormat.DEFAULT)
          .setHeader()
          .setSkipHeaderRecord(true)
          .build()
          .parse(reader);
    } catch (IOException e) {
      throw new ClientRequestException(e.getMessage(), e);
    }

    Vector<String> strings = new Vector<>();
    new ArrayList<>().ensureCapacity(100);
    strings.add("hi");
    List<Record> recordList = StreamSupport.stream(records.spliterator(), false)
        .map(RecordMapper::map)
        .collect(Collectors.toList());
    try {
      csvRepository.saveAll(recordList);
    } catch (DataAccessException dEx) {
      throw new ServerRequestException("Error while saving data", dEx);
    } catch (RuntimeException e) {
      throw new ClientRequestException(e.getMessage(), e);
    }

    new ArrayList<>();
    new LinkedList<>();
    new Vector<>();

    new HashSet<>();
    new LinkedHashSet<>();
    new TreeSet<>();

    HashMap<Integer, Object> map = new HashMap<>();
    map.put(7,"lol");
    new LinkedHashMap<>();
    new TreeMap<>();


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
  public PaginatedRecordsDto<RecordDto> getAllRecords(int limit, int offset) {
    CustomPagealbe paging = new CustomPagealbe(offset, limit);
    Page<RecordDto> records = csvRepository.findAll(paging)
        .map(record -> modelMapper.map(record, RecordDto.class));
    return new PaginatedRecordsDto<>(records);
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
