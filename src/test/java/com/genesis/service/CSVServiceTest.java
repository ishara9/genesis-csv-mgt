package com.genesis.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.genesis.model.Record;
import com.genesis.repository.CSVRepository;
import com.genesis.service.impl.CSVServiceImpl;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

@DataJpaTest
class CSVServiceTest {

  private CSVService csvService;

  @MockBean
  private CSVRepository csvRepository;

  @Spy
  ModelMapper modelMapper;

  @BeforeEach
  void setUp() {
    csvService = new CSVServiceImpl(csvRepository, modelMapper);
  }

  @Test
  void upload() throws IOException {
    MultipartFile multipartFile = new MockMultipartFile("exercise.csv", "Sample csv".getBytes());
//    File file = new File("src/main/resources/sample.csv");
//    multipartFile.transferTo(file);
    csvService.upload(multipartFile);
    verify(csvRepository, atLeastOnce()).saveAll(anyList());
  }

  @Test
  void getRecordById() throws IOException {
    Optional<Record> optionalRecord = Optional.of(Record.builder().code("Type 1").build());
    when(csvRepository.findById(anyString())).thenReturn(optionalRecord);
    assertEquals("Type 1", csvService.getRecordById("Type 1").getCode());
  }

  @Test
  void getRecords() throws IOException {
    Optional<Record> optionalRecord = Optional.of(new Record());
    when(csvRepository.findAll()).thenReturn(List.of(Record.builder().code("Type 1").build()));
    assertEquals("Type 1", csvService.getAllRecords(20, 0).get(0).getCode());
  }

}