package com.assignment.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.FetchType.EAGER;

@Data
@NoArgsConstructor
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@Getter
@Setter
@Entity
@Table(name = "TRANSACTIONS")
public class Transactions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private double amount;
    @Column
    private String currency;
    @Column
    private Date creationDate;
    @Column
    private Date bookingDate;
    @Column
    private String partnerName;
    @Column
    private Long partnerAccountNumb;
    @Column
    private String description;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;
    @ManyToOne
    @JsonIgnore
    private Customer customer;
    @ManyToOne
    @JsonIgnore
    private Account account;

    public Transactions(Long id, double amount, String currency, Date creationDate, Date bookingDate, String partnerName, Long partnerAccountNumb, String description, TransactionStatus status, Account account) {
        this.id = id;
        this.amount = amount;
        this.currency = currency;
        this.creationDate = creationDate;
        this.bookingDate = bookingDate;
        this.partnerName = partnerName;
        this.partnerAccountNumb = partnerAccountNumb;
        this.description = description;
        this.status = status;
        this.account = account;
    }
}