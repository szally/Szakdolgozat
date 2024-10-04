package com.assignment.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.assignment"})
@EnableJpaRepositories(basePackages = {"com.assignment"})
@EntityScan("com.assignment.domain")
public class BankApplication {
    public static void main(String[] args) {

        SpringApplication.run(BankApplication.class, args);
    }

}
