package com.assignment.service;

import com.assignment.domain.Customer;
import com.assignment.domain.Transactions;

public interface TransferService {
    void transferBetweenOwnAccounts(Long sourceAccountNumber, Long destinationAccountNumber, double amount, Transactions transaction, String currency, String description, Customer customer);

    void domesticTransfer(Long sourceAccountNumber, Long destinationAccountNumber, double amount, Transactions transaction, String currency, String description, String partner, Customer customer);

    void internationalTransfer(Long sourceAccountNumber, Long destinationAccountNumber, double amount, Transactions transaction, String currency, String description, String partner, Customer customer, String iban, String swift, String bic);

    void valueDatedTransfer();
}
