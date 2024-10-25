package com.assignment.service;

import com.assignment.domain.TransactionStatus;
import com.assignment.domain.Transactions;
import com.assignment.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService{

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public void updateBookedTransactions(Date now) {
        List<Transactions> transactions = transactionRepository.findAll();
        for (Transactions transaction : transactions) {
            transaction.setBookingDate(now);
            transaction.setStatus(TransactionStatus.BOOKED);
            transactionRepository.save(transaction);
        }
    }
}
