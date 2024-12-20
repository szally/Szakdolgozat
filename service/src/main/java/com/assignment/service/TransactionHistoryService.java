package com.assignment.service;

import com.assignment.domain.Customer;
import com.assignment.domain.Transactions;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TransactionHistoryService {
    List<Transactions>  getTransactionHistory(Customer customer);
}
