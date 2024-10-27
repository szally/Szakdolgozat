package com.assignment.service;

import com.assignment.domain.Account;
import com.assignment.domain.AccountAndCardStatus;
import com.assignment.domain.AccountType;
import com.assignment.domain.Customer;
import com.assignment.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Qualifier("accountServiceImpl")
public class AccountServiceImpl implements AccountService{
    @Autowired
    private AccountRepository accountRepository;
    @Override
    public List<Account> getAccountDetails(Customer customer) {

        return accountRepository.findAccountByCustomer(customer);
    }

    @Override
    public Account openNewAccount(Customer customer, String selectedCurrency, AccountType selectedAccountType) {
        List<Account> accountList = accountRepository.findAll();
        LocalDate openingDate = LocalDate.now();
        Account account = new Account();
        account.setCustomer(customer);
        account.setBalance(0);
        account.setType(selectedAccountType);
        account.setCurrency(selectedCurrency);
        account.setOpeningDate(Date.from(Instant.now()));
        account.setStatus(AccountAndCardStatus.ACTIVE);

        accountList.add(account);
        customer.getAccounts().add(account);

        accountRepository.save(account);
        return account;
    }


    @Override
    public void blockAccount(Account account) {
        if(account.getStatus().equals(AccountAndCardStatus.ACTIVE)){
            account.setStatus(AccountAndCardStatus.BLOCKED);
            accountRepository.save(account);
        }
    }

    @Override
    public void unBlockAccount(Account account) {
        if(account.getStatus().equals(AccountAndCardStatus.BLOCKED)) {
            account.setStatus(AccountAndCardStatus.ACTIVE);
            accountRepository.save(account);
        }
    }

    @Override
    public void closeAccount(Account account) {
        account.setStatus(AccountAndCardStatus.TERMINATED);
        accountRepository.save(account);
    }

    public List<Account> findAllAccounts(){
        return accountRepository.findAll();
    }
    public Account findAccountById(Long accountId){
        return accountRepository.findAccountById(accountId);
    }

    public List<Account> getSavingAccount(Customer customer){
       List<Account> savingAccounts = new ArrayList<Account>();
        return savingAccounts = getAccountDetails(customer).stream().filter(account -> account.getType().equals(AccountType.SAVING)).collect(Collectors.toList());
    }
}
