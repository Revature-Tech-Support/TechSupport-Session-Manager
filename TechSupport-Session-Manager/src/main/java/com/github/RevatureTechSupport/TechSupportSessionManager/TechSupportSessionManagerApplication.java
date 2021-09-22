package com.github.RevatureTechSupport.TechSupportSessionManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class TechSupportSessionManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TechSupportSessionManagerApplication.class, args);
    }
}
