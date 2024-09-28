package com.assignment.persistance.dto;

import com.assignment.domain.Account;
import com.assignment.domain.Customer;
import com.assignment.domain.TransactionStatus;
import lombok.Data;
import lombok.Getter;

import java.util.Date;

@Data
public class TransactionsDTO {
    private Long id;
    private double amount;
    private String currency;
    private Date creationDate;
    private Date bookingDate;
    private String partnerName;
    private Long partnerAccountNumb;
    private String description;
    private TransactionStatus status;
    private Long account;
}
