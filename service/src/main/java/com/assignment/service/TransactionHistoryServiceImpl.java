package com.assignment.service;

import com.assignment.domain.*;
import com.assignment.repository.TransactionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionHistoryServiceImpl implements TransactionHistoryService{

    @Autowired
    TransactionRepository transactionRepository;
    @Override
    public List<Transactions> getTransactionHistory(Customer customer){

        List<Transactions> transactionsList = customer.getTransactionList();

        if (transactionsList.isEmpty()) {
            return Collections.emptyList();
        }
        return transactionsList;
    }

    public List<Transactions> findAllTransactions() {
        return transactionRepository.findAll();
    }
}
