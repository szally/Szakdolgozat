package com.assignment.service;

public interface TransferService {
    void transferBetweenOwnAccounts(String sourceAccountNumber, String destinationAccountNumber, double amount);
    void domesticTransfer(String sourceAccountNumber, String destinationAccountNumber, double amount);
    void internationalTransfer(String sourceAccountNumber, String destinationAccountNumber, double amount);
    void recurringTransfer();//termdeposit

    void valueDatedTransfer();
    void cancelRecurringTransfer();
}
