package com.genesis.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@SpringBootApplication
@ComponentScan(basePackages = {"com.genesis.controller", "com.genesis.config", "com.genesis.service"})
@EnableJpaRepositories(basePackages = "com.genesis.repository")
@EntityScan(basePackages = "com.genesis.model")
public class GenesisApplication {

    public static void main(String[] args) {
        SpringApplication.run(GenesisApplication.class, args);
        try(BufferedReader br = new BufferedReader(new FileReader("text.txt"))){
            String  line;
            while((line = br.readLine())!= null){
                System.out.println(line);
            }
        } catch (IOException e){
            System.out.println("Error"+e.getMessage());
        }
    }

}
