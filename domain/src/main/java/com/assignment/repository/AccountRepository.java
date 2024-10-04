package com.assignment.repository;

import com.assignment.domain.Account;
import com.assignment.domain.AccountAndCardStatus;
import com.assignment.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findAccountByCustomer(Customer customer);
    Account getAccountById(long accountIdToClose);
}
