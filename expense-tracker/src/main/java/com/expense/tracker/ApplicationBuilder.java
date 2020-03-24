package com.expense.tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.expense.tracker")
@EnableAutoConfiguration
public class ApplicationBuilder {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationBuilder.class, args);
    }
}
