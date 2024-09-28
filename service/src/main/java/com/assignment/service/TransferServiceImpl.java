package com.assignment.service;

import com.assignment.domain.*;
import com.assignment.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Service
public class TransferServiceImpl implements TransferService {

    @Autowired
    private TransactionRepository transactionRepository;

    //TO-DO: add exchange rate class and JSON
    @Override
    public void transferBetweenOwnAccounts(Long sourceAccountNumber, Long destinationAccountNumber, double amount, Transactions transaction, String currency, String description, Customer customer) {
        if (sourceAccountNumber == null || destinationAccountNumber == null || amount <= 0) {
            throw new IllegalArgumentException("Invalid input parameters.");
        }

        if (!customer.getAccounts().contains(sourceAccountNumber) || !customer.getAccounts().contains(destinationAccountNumber)) {
            throw new IllegalArgumentException("Source and destination accounts must belong to the same customer.");
        }

        transaction = transfer(sourceAccountNumber, destinationAccountNumber, amount, transaction, currency, description, customer);


        Account destinationAccount = new Account();
        destinationAccount = customer.getAccounts().stream()
                .filter(a -> a.getId().equals(destinationAccountNumber))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Destination account not found"));

        transaction.setPartnerName(customer.getName());
        transaction.setPartnerAccountNumb(destinationAccount.getId());
        destinationAccount.setBalance(destinationAccount.getBalance() + amount);

        customer.getTransactionList().add(transaction);
    }

    @Override
    public void domesticTransfer(Long sourceAccountNumber, Long destinationAccountNumber, double amount, Transactions transaction, String currency, String description, String partner, Customer customer) {
        if (sourceAccountNumber == null || destinationAccountNumber == null || amount <= 0) {
            throw new IllegalArgumentException("Invalid input parameters.");
        }

        transaction = transfer(sourceAccountNumber, destinationAccountNumber, amount, transaction, currency, description, customer);
        transaction.setPartnerName(partner);
        customer.getTransactionList().add(transaction);
    }

    @Override
    public void internationalTransfer(Long sourceAccountNumber, Long destinationAccountNumber, double amount, Transactions transaction, String currency, String description, String partner, Customer customer, String iban, String swift, String bic) {
        if (sourceAccountNumber == null || destinationAccountNumber == null || amount <= 0) {
            throw new IllegalArgumentException("Invalid input parameters.");
        }
        transaction = transfer(sourceAccountNumber, destinationAccountNumber, amount, transaction, currency, description, customer);
        transaction.setPartnerName(partner);
        customer.getTransactionList().add(transaction);

    }

    @Override
    public void valueDatedTransfer() {

    }


    public Transactions transfer(Long sourceAccountNumber, Long destinationAccountNumber, double amount, Transactions transaction, String currency, String description, Customer customer) {
        Account sourceAccount = new Account();
        LocalDate creationDate = LocalDate.now();

        sourceAccount = customer.getAccounts().stream()
                .filter(a -> a.getId().equals(sourceAccountNumber))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Source account not found"));

        transaction.setAccount(sourceAccount);

        transaction.setId((long) (transactionRepository.findAll().size() + 1));
        transaction.setAmount(amount);
        transaction.setCurrency(currency);
        transaction.setCreationDate(Date.from(creationDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        transaction.setBookingDate(Date.from(creationDate.atStartOfDay(ZoneId.systemDefault()).toInstant().plusSeconds(120)));
        transaction.setPartnerAccountNumb(destinationAccountNumber);
        transaction.setDescription(description);
        transaction.setStatus(TransactionStatus.PROCESSED);

       /* if(!sourceAccount.getCurrency().equals(transaction.getCurrency())){
            getExchange(sourceAccount.getCurrency(),transaction.getCurrency(),transaction); //Nem működik, de egyelőre jó placeholder.
        }
*/
        if (sourceAccount.getBalance() < amount) {
            transaction.setStatus(TransactionStatus.FAILED);
            throw new IllegalArgumentException("No enough balance");
        }

        sourceAccount.setBalance(sourceAccount.getBalance() - amount);

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
