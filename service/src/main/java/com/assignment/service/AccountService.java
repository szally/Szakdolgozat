package com.assignment.service;

import com.assignment.domain.Account;
import com.assignment.domain.Customer;

import java.util.List;

public interface AccountService {
    List<Account> getAccountDetails(Customer customer);
    Account openNewAccount(Customer customer, String selectedCurrency);
    void blockAccount(Account account);
    public void unBlockAccount(Account account);
    void closeAccount(Account account);//TERMINATED

}
