package com.assignment.service;

import com.assignment.domain.Account;
import com.assignment.domain.Customer;
import com.assignment.domain.Transactions;
import com.assignment.service.exceptions.TransactionNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TransactionHistoryService {
    List<Transactions>  getTransactionHistory(Customer customer);
    void downloadTransactionHistoryInJSON(List<Transactions> transactions, String filePath);
    void writeToCSV(List<Transactions> transactions, String filePath);
}
