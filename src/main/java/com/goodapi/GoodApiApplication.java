package com.goodapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author msaritas
 */
@ComponentScan({"com.goodapi"})
@SpringBootApplication
public class GoodApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(GoodApiApplication.class, args);
    }
}
