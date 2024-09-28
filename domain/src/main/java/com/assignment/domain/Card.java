package com.assignment.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.FetchType.EAGER;

@Data
@NoArgsConstructor
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@Entity
@Table(name = "CARD")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String customerName;
    @Column
    private Date expiryDate;
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private CardType type;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private AccountAndCardStatus status;
    @Column
    private int pinCode;
    @ManyToOne
    private Customer customer;
    @OneToOne
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