package com.assignment.service;

import com.assignment.domain.*;
import com.assignment.persistance.FileDataStore;
import com.assignment.service.exceptions.TransactionNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionHistoryServiceImpl implements TransactionHistoryService{
    private final FileDataStore dataStore ;

    public TransactionHistoryServiceImpl(FileDataStore dataStore) {
        this.dataStore = dataStore;
    }

    public List<Transactions> getAllTransactions() {
        return dataStore.getTransactions();
    }
    @Override
    public List<Transactions> getTransactionHistory(Customer customer){

        List<Transactions> transactionsList = customer.getTransactionList();

        if (transactionsList.isEmpty()) {
            return Collections.emptyList();
        }

        transactionsList.stream()
                .filter(transactions -> transactions.getBookingDate().equals(LocalDate.now()))
                .peek(transactions -> transactions.setStatus(TransactionStatus.BOOKED))
                .collect(Collectors.toList());
        return transactionsList;
    }

    @Override
    public void downloadTransactionHistoryInJSON(List<Transactions> transactions, String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        try (FileWriter writer = new FileWriter(filePath)) {
            String json = mapper.writeValueAsString(transactions);
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writeToCSV(List<Transactions> transactions, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("Id, Amount, Currency, Creation Date, Booking Date, Partner Name, Partner Account Number, Description, Is Expense, Status, Account\n");

            for (Transactions transaction : transactions) {
                StringBuilder sb = new StringBuilder();
                sb.append(transaction.getId()).append(",");
                sb.append(transaction.getAmount()).append(",");
                sb.append(transaction.getCurrency()).append(",");
                sb.append(transaction.getCreationDate()).append(",");
                sb.append(transaction.getBookingDate()).append(",");
                sb.append(transaction.getPartnerName()).append(",");
                sb.append(transaction.getPartnerAccountNumb()).append(",");
                sb.append(transaction.getDescription()).append(",");
                sb.append(transaction.getStatus()).append(",");
                sb.append(transaction.getAccount()).append("\n");
                writer.write(sb.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
