package com.assignment.domain;

import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@Getter
@Setter
public class Customer extends User{
    private List<Account> accounts = new ArrayList<>();
    private List<Card> cards = new ArrayList<>();
    private List<Transactions> transactionList = new ArrayList<>();

    public Customer(Long id, Credentials credentials, String name, String birthPlace, Date birthDate, String mothersName, String idCardNumb, Long taxNumb, String email, CustomerStatus status, List<Account> accountList, List<Card> cardList, List<Transactions> transactionsList) {
        super(id, name, birthPlace, birthDate, mothersName, idCardNumb, taxNumb, email, credentials, status);
        this.accounts = accountList;
        this.cards = cardList;
        this.transactionList = transactionsList;
    }
}