package com.assignment.service;

import com.assignment.domain.*;
import com.assignment.repository.AccountRepository;
import com.assignment.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Service
public class TransferServiceImpl implements TransferService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;


    //TO-DO: add exchange rate class and JSON
    @Override
    public void transferBetweenOwnAccounts(Long sourceAccountNumber, Long destinationAccountNumber, double amount,  String currency, String description, Customer customer) {
        Transactions transaction = new Transactions();
        if (sourceAccountNumber == null || destinationAccountNumber == null || amount <= 0) {
            throw new IllegalArgumentException("Invalid input parameters.");
        }

        transaction = transfer(sourceAccountNumber, destinationAccountNumber, amount, transaction, currency, description, customer);


        Account destinationAccount = new Account();
        destinationAccount = customer.getAccounts().stream()
                .filter(a -> a.getId().equals(destinationAccountNumber))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Destination account not found"));

        if (!customer.getAccounts().stream().anyMatch(account -> account.getId().equals(sourceAccountNumber))) {
            throw new IllegalArgumentException("Source account not found for the customer.");
        }

        if (!customer.getAccounts().stream().anyMatch(account -> account.getId().equals(destinationAccountNumber))) {
            throw new IllegalArgumentException("Destination account not found for the customer.");
        }

        transaction.setPartnerName(customer.getName());
        transaction.setPartnerAccountNumb(destinationAccount.getId());
        destinationAccount.setBalance(destinationAccount.getBalance() + amount);

        accountRepository.save(destinationAccount);

        transaction.setCustomer(customer);

        customer.getTransactionList().add(transaction);
        transactionRepository.save(transaction);
    }

    @Override
    public void domesticTransfer(Long sourceAccountNumber, Long destinationAccountNumber, double amount, String currency, String description, String partner, Customer customer) {
        Transactions transaction = new Transactions();
        if (sourceAccountNumber == null || destinationAccountNumber == null || amount <= 0) {
            throw new IllegalArgumentException("Invalid input parameters.");
        }

        transaction = transfer(sourceAccountNumber, destinationAccountNumber, amount, transaction, currency, description, customer);
        transaction.setPartnerName(partner);
        customer.getTransactionList().add(transaction);
        transactionRepository.save(transaction);
    }

    @Override
    public void internationalTransfer(Long sourceAccountNumber, Long destinationAccountNumber, double amount, String currency, String description, String partner, Customer customer, String iban, String swift, String bic) {
        Transactions transaction = new Transactions();
        if (sourceAccountNumber == null || destinationAccountNumber == null || amount <= 0) {
            throw new IllegalArgumentException("Invalid input parameters.");
        }
        transaction = transfer(sourceAccountNumber, destinationAccountNumber, amount, transaction, currency, description, customer);
        transaction.setPartnerName(partner);
        customer.getTransactionList().add(transaction);
        transactionRepository.save(transaction);

    }

    @Override
    public void valueDatedTransfer() {

    }


    public Transactions transfer(Long sourceAccountNumber, Long destinationAccountNumber, double amount, Transactions transaction, String currency, String description, Customer customer) {
        Account sourceAccount = new Account();
        Instant creationTimestamp = Instant.now(); // Replace with the actual creation time if needed



        sourceAccount = customer.getAccounts().stream()
                .filter(a -> a.getId().equals(sourceAccountNumber))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Source account not found"));

        transaction.setAccount(sourceAccount);

        transaction.setAmount(amount);
        transaction.setCurrency(currency);
        transaction.setCreationDate(Date.from(creationTimestamp));
        transaction.setBookingDate(Date.from(creationTimestamp.plusSeconds(120)));
        transaction.setPartnerAccountNumb(destinationAccountNumber);
        transaction.setDescription(description);
        transaction.setStatus(TransactionStatus.PROCESSED);

       /* if(!sourceAccount.getCurrency().equals(transaction.getCurrency())){
            getExchange(sourceAccount.getCurrency(),transaction.getCurrency(),transaction); //Nem működik, de egyelőre jó placeholder.
        }
*/
        if (sourceAccount.getBalance() < amount) {
            transaction.setStatus(TransactionStatus.FAILED);
            transaction.setBookingDate(null);
            throw new IllegalArgumentException("No enough balance");
        }

        sourceAccount.setBalance(sourceAccount.getBalance() - amount);
        accountRepository.save(sourceAccount);

        return transaction;
    }

  /*  public void getExchange(String from, String to, Transactions transaction){

        ExchangeRate exchangeRate = getExchangeRate(from, to);

        double convertedAmount = transaction.getAmount() * exchangeRate.getRates().get(to);
        transaction.setAmount(convertedAmount);
        transaction.setCurrency(to);
    }
*/
  /*  private ExchangeRate getExchangeRate(String from, String to) {
        List<ExchangeRate> allExchangeRates = getAllExcangeRates();

        for (ExchangeRate rate : allExchangeRates) {
            if (rate.getRates().containsKey(from) && rate.getRates().containsKey(to)) {
                return rate;
            }
        }

        // Handle the case where the exchange rate is not found
        throw new IllegalArgumentException("Exchange rate not found for currencies: " + from + " and " + to);
    }*/
}
