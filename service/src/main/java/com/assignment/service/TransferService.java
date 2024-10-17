package com.assignment.service;

import com.assignment.domain.Customer;
import com.assignment.domain.Transactions;
import com.assignment.service.exceptions.InsufficientFundsException;
import com.assignment.service.exceptions.InvalidIbanException;
import com.assignment.service.exceptions.PartnerBankNotFoundException;
import org.springframework.stereotype.Service;

@Service
public interface TransferService {
    void transferBetweenOwnAccounts(Long sourceAccountNumber, Long destinationAccountNumber, double amount, String currency, String description, Customer customer) throws InsufficientFundsException;

    void domesticTransfer(Long sourceAccountNumber, Long destinationAccountNumber, double amount, String currency, String description, String partner, Customer customer) throws InsufficientFundsException;

    void internationalTransfer(Long sourceAccountNumber, Long destinationAccountNumber, double amount, String currency, String description, String partner, Customer customer, String iban, String swift) throws InsufficientFundsException, PartnerBankNotFoundException, InvalidIbanException;

    void valueDatedTransfer(long sourceAccountNumber, long toAccount, double amount, String currency, Customer customer) throws InsufficientFundsException;
}
