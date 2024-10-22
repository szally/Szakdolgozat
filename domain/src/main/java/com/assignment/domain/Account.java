package com.assignment.domain;

import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

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
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private AccountType type;
    @OneToOne
    private Iban iban;
    @ManyToOne
    private Customer customer;
    @OneToOne
    private Card card;
    @OneToMany(mappedBy = "id")
    private List<Transactions> transactionsList;
}