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
}