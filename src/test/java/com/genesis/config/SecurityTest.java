package com.genesis.config;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.genesis.controller.CSVController;
import com.genesis.repository.CSVRepository;
import com.genesis.service.CSVService;
import com.genesis.service.impl.CSVServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

@ContextConfiguration(classes = {SecurityConfig.class, CSVController.class, CSVService.class,
    CSVRepository.class})
@ExtendWith(SpringExtension.class)
@WebMvcTest(value = CSVController.class)
public class SecurityTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private CSVServiceImpl csvService;

  @Test
  public void givenNotAuth_whenAddFoo_thenUnauthorized() throws Exception {
    mvc.perform(
        MockMvcRequestBuilders.post("/api/v1/csv/sec")
            .contentType(MediaType.APPLICATION_JSON)
            .content(createFoo())
            .with(testUser())
    ).andExpect(status().isForbidden());
  }

  @Test
  public void givenCsrf_whenAddFoo_thenCreated() throws Exception {
    mvc.perform(
        MockMvcRequestBuilders.post("/api/v1/csv/sec")
            .contentType(MediaType.APPLICATION_JSON)
            .content(createFoo())
            .with(testUser())
            .with(csrf())
    ).andExpect(status().isCreated());
  }

  RequestPostProcessor testUser() {
    return user("user1").password("user1Pass").roles("USER");
  }

  String createFoo() throws JsonProcessingException {
    return new ObjectMapper().writeValueAsString(new Foo(randomAlphabetic(6)));
  }
}
