package com.genesis.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.genesis.model.Student;
import com.genesis.repository.StudentRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.shaded.org.hamcrest.CoreMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class StudentControllerTest {

  @Autowired
  private StudentRepository studentRepository;

  @Autowired
  private MockMvc mockMvc;

  //BDD- behavior driven development
  @Test
  public void givenStudents_whenGetAllStudents_thenListOfStudent() throws Exception {
    //given - setup precondition
    List<Student> students = List.of(
        Student.builder().name("Ishrav").email("e@mail.com").build(),
        Student.builder().name("Ishrav2").email("e2@mail.com").build()
    );
    studentRepository.saveAll(students);

    //when - action
    ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/students"));

    //then - verify output
    response.andExpect(MockMvcResultMatchers.status().isOk());
    response.andExpect(jsonPath("$.size()").value(students.size()));
  }

  @Test
  public void createStudent() {
  }

  @Test
  public void getAllStudents() {
  }
}