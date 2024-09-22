package com.assignment.persistance.dto;

import com.assignment.domain.AccountAndCardStatus;
import com.assignment.domain.Card;
import com.assignment.domain.Customer;
import lombok.Data;

import java.util.Date;

@Data
public class AccountDTO {
    private Long id;
    private double balance;
    private String currency;
    private Date openingDate;
    private AccountAndCardStatus status;
    private Long card;

}
