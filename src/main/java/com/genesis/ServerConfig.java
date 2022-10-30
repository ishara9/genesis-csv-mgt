package com.genesis;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class ServerConfig {
  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

  @Autowired
  Environment environment;

  //Initialized as bean
  @Bean
  CommandLineRunner commandLineRunner() {
    return args -> {
      //code executed when application starts
      LOGGER.info("Well-done app run successfully!");
      System.out.println(environment.getProperty("info.app.name"));
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
