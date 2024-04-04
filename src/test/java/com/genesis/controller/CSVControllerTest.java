package com.genesis.controller;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.genesis.config.Foo;
import com.genesis.config.SecurityConfig;
import com.genesis.dto.PaginatedRecordsDto;
import com.genesis.dto.RecordDto;
import com.genesis.repository.CSVRepository;
import com.genesis.service.CSVService;
import com.genesis.service.impl.CSVServiceImpl;
import java.io.FileInputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.web.multipart.MultipartFile;

@ContextConfiguration(classes = {SecurityConfig.class, CSVController.class, CSVService.class,
    CSVRepository.class, ModelMapper.class})
@ExtendWith(SpringExtension.class)
@WebMvcTest(value = CSVController.class)
class CSVControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private CSVServiceImpl csvService;

  public void uploadFile_success() throws Exception {
    FileInputStream csvStream = new FileInputStream("src/main/resources/exercise.csv");
    MultipartFile multipartFile = new MockMultipartFile("src/main/resources/exercise.csv", csvStream);

    mvc.perform(MockMvcRequestBuilders.multipart("/api/v1/csv")
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .content(multipartFile.getBytes())
            .param("file", "4"))
        .andExpect(status().isCreated());
  }

  @Test
  public void getRecordById_success() throws Exception {

    String recordId = "12345";
    RecordDto record = buildDummyRecord(recordId);
    given(csvService.getRecordById(anyString())).willReturn(record);

    mvc.perform((MockMvcRequestBuilders.get("/api/v1/csv/records/" + recordId)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.code").value(recordId))
        .andExpect(jsonPath("$.source").value(record.getSource()))
        .andExpect(jsonPath("$.codeListCode").value(record.getCodeListCode()))
        .andExpect(jsonPath("$.displayValue").value(record.getDisplayValue()))
        .andExpect(jsonPath("$.longDescription").value(record.getLongDescription()))
        .andExpect(jsonPath("$.sortingPriority").value(record.getSortingPriority()));
  }

  @Test
  public void getAllRecords_success() throws Exception {

    String recordId = "12345";
    RecordDto record = buildDummyRecord(recordId);
    List<RecordDto> records = new ArrayList<>();
    records.add(record);
    Page<RecordDto> pageDto = new PageImpl<>(records, PageRequest.ofSize(1), 1);
    PaginatedRecordsDto<RecordDto> paginatedRecordsDto = new PaginatedRecordsDto<>(pageDto);
    paginatedRecordsDto.setContent(records);
    given(csvService.getAllRecords(anyInt(), anyInt())).willReturn(paginatedRecordsDto);

    mvc.perform((MockMvcRequestBuilders.get("/api/v1/csv/records"))).andExpect(status().isOk())
        .andExpect(jsonPath("$").isNotEmpty())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.content[0].code").value(recordId));
  }

  private RecordDto buildDummyRecord(String id) {

    return RecordDto.builder()
        .code(id)
        .source("ZIB")
        .codeListCode("ZIB001")
        .displayValue("Polsslag regelmatig")
        .longDescription("The long description is necessary")
        .fromDate(new Date(System.currentTimeMillis()))
        .toDate(new Date(System.currentTimeMillis()))
        .sortingPriority(1L)
        .build();
  }

  @Test
  public void givenNotAuth_whenAddFoo_thenUnauthorized() throws Exception {
    mvc.perform(
        MockMvcRequestBuilders.post("/api/v1/csv/sec").contentType(MediaType.APPLICATION_JSON)
            .content(createFoo())
            .with(testUser())
    ).andExpect(status().isForbidden());
  }

  @Test
  public void givenCsrf_whenAddFoo_thenCreated() throws Exception {
    mvc.perform(
        MockMvcRequestBuilders.post("/api/v1/csv/sec").contentType(MediaType.APPLICATION_JSON)
            .content(createFoo())
            .with(testUser()).with(csrf())
    ).andExpect(status().isCreated());
  }


  RequestPostProcessor testUser() {
    return user("user1").password("user1Pass").roles("USER");
  }

  RequestPostProcessor testAdmin() {
    return user("admin").password("adminPass").roles("USER", "ADMIN");
  }

  String createFoo() throws JsonProcessingException {
    return new ObjectMapper().writeValueAsString(new Foo(randomAlphabetic(6)));
  }
}