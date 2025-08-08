package com.genesis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.genesis.repository")
@EntityScan(basePackages = "com.genesis.model")
public class GenesisApplication {

    public static void main(String[] args) {
        SpringApplication.run(GenesisApplication.class, args);
    }

}
