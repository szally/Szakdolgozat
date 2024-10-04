package com.assignment.domain;

import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@Getter
@Setter
@Entity
@Table(name = "CUSTOMER")
public class Customer extends User{
    @OneToMany(mappedBy = "customer")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Account> accounts = new ArrayList<>();
    @OneToMany(mappedBy = "customer")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Card> cards = new ArrayList<>();
    @OneToMany(mappedBy = "customer")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Transactions> transactionList = new ArrayList<>();

    public Customer(Long id, Credentials credentials, String name, String birthPlace, Date birthDate, String mothersName, String idCardNumb, Long taxNumb, String email, CustomerStatus status, List<Account> accountList,List<Transactions> transactionsList) {
        super(id, name, birthPlace, birthDate, mothersName, idCardNumb, taxNumb, email, credentials, status);
        this.accounts = accountList;
        this.transactionList = transactionsList;
    }
}