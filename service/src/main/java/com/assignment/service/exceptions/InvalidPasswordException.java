package com.assignment.service.exceptions;

public class InvalidPasswordException extends Throwable {
    public InvalidPasswordException(String s) {
        System.out.print(s);
    }
}
