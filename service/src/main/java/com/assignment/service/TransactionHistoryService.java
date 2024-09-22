package com.assignment.service;

import com.assignment.domain.Account;
import com.assignment.domain.Customer;
import com.assignment.domain.Transactions;
import com.assignment.service.exceptions.TransactionNotFoundException;

import java.util.List;

public interface TransactionHistoryService {
    List<Transactions>  getTransactionHistory(Customer customer);
    void downloadTransactionHistoryInJSON(List<Transactions> transactions, String filePath);
    void writeToCSV(List<Transactions> transactions, String filePath);
}
