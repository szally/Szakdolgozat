package com.assignment.domain;

import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@Getter
@Setter
public class Card {
    private Long id;
    private String customerName;
    private Date expiryDate;
    private CardType type;
    private AccountAndCardStatus status;
    private int pinCode;
    private Customer customer;
    private Account account;

    public Card(Long id, String customerName, Date expiryDate, CardType type, AccountAndCardStatus status, int pinCode) {
        this.id = id;
        this.customerName = customerName;
        this.expiryDate = expiryDate;
        this.type = type;
        this.status = status;
        this.pinCode = pinCode;
    }
}