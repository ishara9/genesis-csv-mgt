package com.genesis.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import com.genesis.dto.MedDTO;
import com.genesis.exception.ServerRequestException;
import com.genesis.model.Record;
import com.genesis.repository.CSVRepository;
import com.genesis.service.impl.CSVServiceImpl;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class CSVServiceTestIT {

  private CSVService CSVService;

  @Autowired
  private CSVRepository CSVRepository;

  @BeforeEach
  void setUp() {
    CSVService = new CSVServiceImpl(CSVRepository, new ModelMapper());
  }

  @AfterEach
  void tearDown() {
    CSVRepository.deleteAll();
  }

  @Test
  void getMeds_whenAtLeastOneMedAvailable_returnMed() {
    Record record = new Record(1L, "name", "email@mail.com", "1x3i3t");
    CSVRepository.saveAll(List.of(record));
    assertEquals("name", CSVService.getMeds().get(0).getName());
  }

  @Test
  void getMed_whenAMedIsThere_getThatMed() {
    Record record = new Record(1L, "name", "email@mail.com", "1x3i3t");
    CSVRepository.saveAll(List.of(record));
    assertEquals("name", CSVService.getMed(1L).getName());
  }

  @Test
  void createMeds_whenMedIsAvailable_shouldCreateAMed() {
    MedDTO MedDTO = new MedDTO(1L, "name", "email@mail.com", "1x3i3t");
    CSVService.createMeds(List.of(MedDTO));
    assertEquals("name", CSVService.getMed(1L).getName());
  }

  @Test()
  void deleteMedById_whenMedIsDeleted_expectedException() {
    Record record = new Record(1L, "name", "email@mail.com", "1x3i3t");
    CSVRepository.saveAll(List.of(record));
    CSVService.deleteMedById(1L);
    assertThrowsExactly(ServerRequestException.class, () -> CSVService.getMed(1L));
  }

  @Test
  void updateMed_changedDetails_expectChanges() {
    Record record = new Record(1L, "name", "email@mail.com", "1x3i3t");
    CSVRepository.saveAll(List.of(record));
    CSVService.updateMed(new MedDTO(1L, "name2", "email2@mail.com", "no secret"), 1L);
    assertEquals("name2", CSVService.getMed(1L).getName());
  }

  @Test
  void updatePartialMed_whenChangesPatched_getPatchedChanges() {
    Record record = new Record(1L, "name", "email@mail.com", "1x3i3t");
    CSVRepository.saveAll(List.of(record));
    CSVService.updatePartialMed(MedDTO.builder().name("namePatched").build(), 1L);
    assertEquals("namePatched", CSVService.getMed(1L).getName());
  }


}