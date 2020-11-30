package com.goodapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

/**
 * @author msaritas
 */
@OpenAPIDefinition(info = @Info(title = "Good API", version = "1.0"))
@ComponentScan({ "com.goodapi" })
@SpringBootApplication
public class GoodApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(GoodApiApplication.class, args);
    }
}
