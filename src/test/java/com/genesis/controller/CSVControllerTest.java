package com.genesis.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;

import com.genesis.dto.MedDTO;
import com.genesis.service.impl.CSVServiceImpl;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = CSVController.class)
class CSVControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CSVServiceImpl medsService;


  @Test
  void getMeds() throws Exception {
    List<MedDTO> mockMeds = List.of(new MedDTO(1L, "name", "mail@mail.com", "1x2i3e"));
    Mockito.when(medsService.getMeds()).thenReturn(mockMeds);

    RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
        "/meds/api/v2/meds").accept(
        MediaType.APPLICATION_JSON);

    MvcResult result = mockMvc.perform(requestBuilder).andReturn();

    String expected = "[{\"name\":\"name\",\"email\":\"mail@mail.com\",\"id\":1}]";

    JSONAssert.assertEquals(expected, result.getResponse()
        .getContentAsString(), false);
  }

  @Test
  void getMed() throws Exception {
    MedDTO mockMed = new MedDTO(1L, "name", "mail@mail.com", "1x2i3e");
    Mockito.when(medsService.getMed(anyLong())).thenReturn(mockMed);

    RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
        "/meds/api/v2/meds/1").accept(
        MediaType.APPLICATION_JSON);

    MvcResult result = mockMvc.perform(requestBuilder).andReturn();

    String expected = "{\"name\":\"name\",\"email\":\"mail@mail.com\",\"id\":1}";

    JSONAssert.assertEquals(expected, result.getResponse()
        .getContentAsString(), false);
  }

  @Test
  void addMeds() throws Exception {
    doNothing().when(medsService).createMeds(anyList());
    String input = "[{\"name\":\"name\",\"email\":\"mail@mail.com\",\"medId\":1}]";
    RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/meds/api/v2/meds")
        .content(input)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON);

    MvcResult result = mockMvc.perform(requestBuilder).andReturn();

    assertEquals(201, result.getResponse().getStatus());
  }

  @Test
  void updateMed() throws Exception {
    doNothing().when(medsService).updateMed(any(MedDTO.class), anyLong());
    String input = "{\"name\":\"name\",\"email\":\"mail@mail.com\",\"medId\":1}";
    RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/meds/api/v2/meds/1")
        .content(input)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON);

    MvcResult result = mockMvc.perform(requestBuilder).andReturn();

    assertEquals(200, result.getResponse().getStatus());
  }

  @Test
  void updatePatchMed() throws Exception {
    doNothing().when(medsService).updateMed(any(MedDTO.class), anyLong());
    String input = "{\"name\": \"M-16\" }";
    RequestBuilder requestBuilder = MockMvcRequestBuilders.patch("/meds/api/v2/meds/1")
        .content(input)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON);

    MvcResult result = mockMvc.perform(requestBuilder).andReturn();

    assertEquals(200, result.getResponse().getStatus());
  }

  @Test
  void deleteMed() throws Exception {
    doNothing().when(medsService).updateMed(any(MedDTO.class), anyLong());
    RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/meds/api/v2/meds/1")
        .accept(MediaType.APPLICATION_JSON);

    MvcResult result = mockMvc.perform(requestBuilder).andReturn();

    assertEquals(200, result.getResponse().getStatus());
  }
}