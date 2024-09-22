package com.assignment.domain;

import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@Getter
@Setter
public class Transactions {
    private Long id;
    private double amount;
    private String currency;
    private Date creationDate;
    private Date bookingDate;
    private String partnerName;
    private Long partnerAccountNumb;
    private String description;
    private boolean isExpense;
    private TransactionStatus status;
    private Customer customer;
    private Account account;

    public Transactions(Long id, double amount, String currency, Date creationDate, Date bookingDate, String partnerName, Long partnerAccountNumb, String description, boolean expense, TransactionStatus status, Account account) {
        this.id = id;
        this.amount = amount;
        this.currency = currency;
        this.creationDate = creationDate;
        this.bookingDate = bookingDate;
        this.partnerName = partnerName;
        this.partnerAccountNumb = partnerAccountNumb;
        this.description = description;
        this.isExpense = expense;
        this.status = status;
        this.account = account;
    }

    public boolean getIsExpense() {
        return isExpense;
    }
}