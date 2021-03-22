package com.dentistapp.dentistappdevelop;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class DentistAppDevelopApplication {

    public static void main(String[] args) {
        SpringApplication.run(DentistAppDevelopApplication.class, args);
    }

}
