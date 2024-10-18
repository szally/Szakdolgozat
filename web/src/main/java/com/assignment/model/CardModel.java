package com.assignment.model;

import com.assignment.domain.AccountAndCardStatus;
import com.assignment.domain.CardType;
import com.assignment.domain.Customer;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
public class CardModel {
    private Long id;
    private String customerName;
    private Date expiryDate;
    private CardType type;
    private AccountAndCardStatus status;
    private int pinCode;
    private Customer customer;
}
