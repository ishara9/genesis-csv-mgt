package com.genesis.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.genesis.dto.MedDTO;
import com.genesis.model.Record;
import com.genesis.repository.CSVRepository;
import com.genesis.service.impl.CSVServiceImpl;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@DataJpaTest
class CSVServiceTest {

  private CSVService CSVService;

  @MockBean
  private CSVRepository CSVRepository;

  @Spy
  ModelMapper modelMapper;

  @BeforeEach
  void setUp() {
    CSVService = new CSVServiceImpl(CSVRepository, modelMapper);
  }

  @AfterEach
  void tearDown() {
    CSVRepository.deleteAll();
  }

  @Test
  void getMeds_whenAtLeastOneMedAvailable_returnMed() {
    Record record = new Record(1L, "name", "email@mail.com", "1x3i3t");
    when(CSVRepository.findAll()).thenReturn(List.of(record));
    Assertions.assertEquals("name", CSVService.getMeds().get(0).getName());
  }

  @Test
  void getMed_whenAMedIsThere_getThatMed() {
    Record record = new Record(1L, "name", "email@mail.com", "1x3i3t");
    when(CSVRepository.findAll()).thenReturn(List.of(record));
    Assertions.assertEquals("name", CSVService.getMed(1L).getName());
  }

  @Test
  void createMeds_whenMedIsAvailable_shouldCreateAMed() {
    MedDTO medDTO = new MedDTO(1L, "name", "email@mail.com", "1x3i3t");
    CSVService.createMeds(List.of(medDTO));
    verify(CSVRepository, atMostOnce()).saveAll(anyList());
  }

  @Test()
  void deleteMedById_whenMedIsDeleted_calledDeleteMethodAtLeastOnce() {
    CSVService.deleteMedById(1L);
    verify(CSVRepository, atLeastOnce()).deleteById(any());
  }

}