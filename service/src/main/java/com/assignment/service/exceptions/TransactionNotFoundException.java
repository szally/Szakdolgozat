package com.assignment.service.exceptions;

public class TransactionNotFoundException extends Throwable {
    public TransactionNotFoundException(String message) {
        super(message);
    }
}
