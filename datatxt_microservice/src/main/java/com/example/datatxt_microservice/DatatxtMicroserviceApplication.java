package com.example.datatxt_microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DatatxtMicroserviceApplication {

    public static void main(String[] args) {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        SpringApplication.run(DatatxtMicroserviceApplication.class, args);
        System.out.println("Running.");
    }
}
