package com.assignment.app;

import com.assignment.service.AccountService;
import com.assignment.service.AccountServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.assignment.app.view")
public class AppConfig {
    @Bean
    public AccountService accountService() {
        return new AccountServiceImpl();
    }
}
