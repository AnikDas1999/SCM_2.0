package com.scm20;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.scm20") // 👈 UPDATED TO MATCH YOUR NEW PACKAGE STRUCTURE!
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}