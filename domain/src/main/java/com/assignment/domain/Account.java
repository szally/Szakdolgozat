package com.assignment.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

import static javax.persistence.FetchType.EAGER;

@Data
@NoArgsConstructor
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@Getter
@Setter
@Entity
@Table(name = "ACCOUNT")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private double balance;
    @Column
    private String currency;
    @Column
    private Date openingDate;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private AccountAndCardStatus status;
    @ManyToOne
    private Customer customer;
    @OneToOne
    private Card card;
    @OneToMany(mappedBy = "id")
    private List<Transactions> transactionsList;

    public Account(Long id, double balance, String currency, Date openingDate, AccountAndCardStatus status, Card card) {
        this.id = id;
        this.balance = balance;
        this.currency = currency;
        this.openingDate = openingDate;
        this.status = status;
        this.card = card;
    }
}