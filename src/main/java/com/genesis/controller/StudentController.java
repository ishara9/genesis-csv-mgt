package com.genesis.controller;

import com.genesis.model.Student;
import com.genesis.repository.StudentRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/students")
@AllArgsConstructor
public class StudentController {

  @Autowired
  private final StudentRepository studentRepository;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Student createStudent(@RequestBody Student student){
    return studentRepository.save(student);
  }

  @GetMapping
  public List<Student> getAllStudents(){
    return studentRepository.findAll();
  }
}
