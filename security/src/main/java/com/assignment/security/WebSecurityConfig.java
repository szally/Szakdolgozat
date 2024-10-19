package com.assignment.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
    public WebSecurityConfig() {
    }

    protected void configure(HttpSecurity http) throws Exception{
        ((HttpSecurity)((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)http.authorizeRequests()
                .antMatchers(new String[]{"/home"})).permitAll()
                .antMatchers(new String[]{"/accounts"})).permitAll()
                .antMatchers(new String[]{"/cards"})).permitAll()
                .antMatchers(new String[]{"/transactions"})).permitAll()
                .antMatchers(new String[]{"/transfer-between-own-accounts"})).permitAll()
                .antMatchers(new String[]{"/static/**"})).permitAll()
                .antMatchers(new String[]{"/login"})).permitAll().anyRequest()).authenticated().and()).formLogin()
                .defaultSuccessUrl("/home").permitAll();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

}
