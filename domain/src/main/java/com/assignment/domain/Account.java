package com.assignment.domain;

import lombok.*;
import org.apache.catalina.core.StandardHost;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@Getter
@Setter
public class Account {
    private Long id;
    private double balance;
    private String currency;
    private Date openingDate;
    private AccountAndCardStatus status;
    private Customer customer;
    private Card card;

    public Account(Long id, double balance, String currency, Date openingDate, AccountAndCardStatus status, Card card) {
        this.id = id;
        this.balance = balance;
        this.currency = currency;
        this.openingDate = openingDate;
        this.status = status;
        this.card = card;
    }
}