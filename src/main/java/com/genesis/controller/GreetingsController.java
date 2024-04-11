package com.genesis.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingsController {

  @GetMapping("/")
  public String hello() {
    return "Hello world!";
  }

  @GetMapping("/secured")
  public String secured() {
    return "Hello secured!";
  }



}
