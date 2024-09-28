package com.assignment.service;

import com.assignment.domain.Account;
import com.assignment.domain.AccountAndCardStatus;
import com.assignment.domain.Customer;
import com.assignment.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService{
    @Autowired
    private AccountRepository accountRepository;
    @Override
    public List<Account> getAccountDetails(Customer customer) {

        return accountRepository.findAccountByCustomer(customer);
    }

    @Override
    public Account openNewAccount(Customer customer, String selectedCurrency) {
        List<Account> accountList = accountRepository.findAll();
        LocalDate openingDate = LocalDate.now();
        Account account = new Account();
        account.setId((long) (accountList.size() + 1));
        account.setCustomer(customer);
        account.setBalance(0);
        account.setCurrency(selectedCurrency);
        account.setOpeningDate(Date.from(openingDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        account.setStatus(AccountAndCardStatus.ACTIVE);

        accountList.add(account);
        customer.getAccounts().add(account);
        return account;
    }


    @Override
    public void blockAccount(Account account) {
        account.setStatus(AccountAndCardStatus.BLOCKED);
    }

    @Override
    public void unBlockAccount(Account account) {
        account.setStatus(AccountAndCardStatus.ACTIVE);
    }

    @Override
    public void closeAccount(Account account) {
        account.setStatus(AccountAndCardStatus.TERMINATED);
    }
}
