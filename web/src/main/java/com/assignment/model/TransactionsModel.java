package com.assignment.model;

import com.assignment.domain.Account;
import com.assignment.domain.Customer;
import com.assignment.domain.TransactionStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
public class TransactionsModel {
    private Long id;
    private double amount;
    private String currency;
    private Date creationDate;
    private Date bookingDate;
    private String partnerName;
    private Long partnerAccountNumb;
    private String description;
    private TransactionStatus status;
    private Customer customer;
    private Account account;
}
