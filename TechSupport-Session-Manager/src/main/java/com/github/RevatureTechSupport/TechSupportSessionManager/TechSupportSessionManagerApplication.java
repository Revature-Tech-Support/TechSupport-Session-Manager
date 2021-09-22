package com.github.RevatureTechSupport.TechSupportSessionManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableEurekaClient
public class TechSupportSessionManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TechSupportSessionManagerApplication.class, args);
    }
}
