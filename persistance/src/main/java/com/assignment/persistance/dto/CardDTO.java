package com.assignment.persistance.dto;

import com.assignment.domain.Account;
import com.assignment.domain.AccountAndCardStatus;
import com.assignment.domain.CardType;
import com.assignment.domain.Customer;
import lombok.Data;

import java.util.Date;

@Data
public class CardDTO {
    private Long id;
    private String customerName;
    private Date expiryDate;
    private CardType type;
    private AccountAndCardStatus status;
    private int pinCode;
}
