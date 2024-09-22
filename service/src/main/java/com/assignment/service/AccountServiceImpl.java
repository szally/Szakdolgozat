package com.assignment.service;

import com.assignment.domain.Account;
import com.assignment.domain.AccountAndCardStatus;
import com.assignment.domain.Customer;
import com.assignment.persistance.FileDataStore;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class AccountServiceImpl implements AccountService{
    private final FileDataStore dataStore ;

    public AccountServiceImpl(FileDataStore dataStore) {
        this.dataStore = dataStore;
    }

    public List<Account> getAllAccounts() {
        return dataStore.getAccounts();
    }
    @Override
    public List<Account> getAccountDetails(Customer customer) {
        List<Account> allAccounts = getAllAccounts();
        //List<Account> accountList = new ArrayList<>();

        /*if (customer.getAccounts().isEmpty()) {
            System.out.println("Customer has no associated accounts.");
            return accountList;
        }

        for (Account accountInAll : allAccounts) {
            for (Account accountInCustomer : customer.getAccounts()) {
                if (accountInAll.getId().equals(accountInCustomer.getId())) {
                    if (!accountInAll.getStatus().equals(AccountAndCardStatus.TERMINATED)) {
                        accountList.add(accountInAll);
                        break;
                    }
                }
            }
        }*/

        List<Account> accountList = allAccounts.stream()
                .filter(account -> customer
                        .getAccounts()
                        .stream()
                        .anyMatch(accountCustomer -> accountCustomer
                                .getId()
                                .equals(accountCustomer
                                        .getId())))
                .collect(Collectors.toList());

        if (accountList.isEmpty()) {
            return Collections.emptyList();
        }

        customer.setAccounts(accountList);
        return accountList;
    }

    @Override
    public Account openNewAccount(Customer customer, String selectedCurrency) {
        List<Account> accountList = getAllAccounts();
        LocalDate openingDate = LocalDate.now();
        Account account = new Account();
        account.setCustomer(customer);
        account.setBalance(0);
        account.setCurrency(selectedCurrency);
        account.setOpeningDate(Date.from(openingDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        account.setStatus(AccountAndCardStatus.ACTIVE);

        accountList.add(account);

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
