package com.assignment.service;

import com.assignment.domain.Account;
import com.assignment.domain.AccountType;
import com.assignment.domain.Customer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AccountService {
    List<Account> getAccountDetails(Customer customer);

    Account openNewAccount(Customer customer, String selectedCurrency, AccountType selectedAccountType);

    void blockAccount(Account account);
    public void unBlockAccount(Account account);
    void closeAccount(Account account);//TERMINATED

}
