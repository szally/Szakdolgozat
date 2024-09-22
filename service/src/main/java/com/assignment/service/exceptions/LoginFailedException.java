package com.assignment.service.exceptions;

public class LoginFailedException extends Exception {
    public LoginFailedException(String message) {
        System.out.print(message);
    }
}
