package com.assignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"com.assignment"})
@EnableJpaRepositories(basePackages = {"com.assignment"})
@EntityScan("com.assignment.domain")
@EnableScheduling
public class BankApplication {
    public static void main(String[] args) {

        SpringApplication.run(BankApplication.class, args);
    }

}
