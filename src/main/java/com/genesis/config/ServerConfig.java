package com.genesis.config;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@Slf4j
public class ServerConfig {

  @Autowired
  Environment environment;

  //Initialized as bean
  @Bean
  CommandLineRunner commandLineRunner() {
    return args -> {
      //code executed when application starts
      log.info("Genesis CSV Management application started successfully.");
    };
  }

  @Bean
  public ModelMapper modelMapper() {
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration()
        .setFieldMatchingEnabled(true)
        .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
    return modelMapper;
  }

}
