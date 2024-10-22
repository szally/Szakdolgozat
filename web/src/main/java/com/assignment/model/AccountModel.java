package com.assignment.model;

import com.assignment.domain.*;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@Getter
@Setter
public class AccountModel {

    private Long id;
    private double balance;
    private String currency;
    private Date openingDate;
    private AccountAndCardStatus status;
    private AccountType type;
    private Iban iban;
    private Customer customer;
    private Card card;
    private List<Transactions> transactionsList;
}
