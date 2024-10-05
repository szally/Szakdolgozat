package com.assignment.service;

import com.assignment.domain.*;
import com.assignment.repository.*;
import com.assignment.service.exceptions.InsufficientFundsException;
import com.assignment.service.exceptions.InvalidIbanException;
import com.assignment.service.exceptions.PartnerBankNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
public class TransferServiceImpl implements TransferService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    ExchangeRateRepository exchangeRateRepository;

    @Autowired
    PartnerBankRepository partnerBankRepository;

    @Autowired
    IbanCodeRepository ibanCodeRepository;

    @Override
    public void transferBetweenOwnAccounts(Long sourceAccountNumber, Long destinationAccountNumber, double amount,  String currency, String description, Customer customer) throws InsufficientFundsException {
        Account sourceAccount = accountRepository.findAccountById(sourceAccountNumber);
        Account destinationAccount = accountRepository.findAccountById(destinationAccountNumber);
        Transactions transaction = new Transactions();

        transaction.setAccount(sourceAccount);

        transaction.setAmount(amount);
        transaction.setCurrency(currency);
        transaction.setCreationDate(Date.from(Instant.now()));
        transaction.setBookingDate(Date.from(Instant.now().plusSeconds(120)));
        transaction.setPartnerName(customer.getName());
        transaction.setPartnerAccountNumb(destinationAccountNumber);
        transaction.setDescription(description);
        transaction.setStatus(TransactionStatus.PROCESSED);
        transaction.setCustomer(customer);

        double amountToTransfer;
        if (sourceAccount.getCurrency().equals(destinationAccount.getCurrency())) {
            amountToTransfer = amount;
        } else if((transaction.getCurrency().equals(sourceAccount.getCurrency()))) {
            amountToTransfer = convertAmount(sourceAccount.getCurrency(), destinationAccount.getCurrency(), amount);
            sourceAccount.setBalance(sourceAccount.getBalance() - amount);
            destinationAccount.setBalance(destinationAccount.getBalance() + amountToTransfer);
        }else {
            amountToTransfer = convertAmount(destinationAccount.getCurrency(), sourceAccount.getCurrency(), amount);
            sourceAccount.setBalance(sourceAccount.getBalance() - amountToTransfer);
            destinationAccount.setBalance(destinationAccount.getBalance() + amount);
        }

        if (sourceAccount.getBalance() < 0) {
            throw new InsufficientFundsException("Insufficient funds in source account");
        }

        accountRepository.save(sourceAccount);
        accountRepository.save(destinationAccount);

        customer.getTransactionList().add(transaction);
        transactionRepository.save(transaction);
    }

    @Override
    public void domesticTransfer(Long sourceAccountNumber, Long destinationAccountNumber, double amount, String currency, String description, String partner, Customer customer) {
        Transactions transaction = new Transactions();
        if (sourceAccountNumber == null || destinationAccountNumber == null || amount <= 0) {
            throw new IllegalArgumentException("Invalid input parameters.");
        }
        Account sourceAccount = accountRepository.findAccountById(sourceAccountNumber);

        transaction.setAccount(sourceAccount);
        transaction.setAmount(amount);
        transaction.setCurrency(currency);
        transaction.setCreationDate(Date.from(Instant.now()));
        transaction.setBookingDate(Date.from(Instant.now().plusSeconds(120)));
        transaction.setPartnerName(partner);
        transaction.setPartnerAccountNumb(destinationAccountNumber);
        transaction.setDescription(description);
        transaction.setStatus(TransactionStatus.PROCESSED);
        transaction.setCustomer(customer);

        double amountToTransfer;

        if(!transaction.getCurrency().equals(sourceAccount.getCurrency())){
            amountToTransfer = convertAmount(currency, sourceAccount.getCurrency(), amount);
            sourceAccount.setBalance(sourceAccount.getBalance() - amountToTransfer);
        }
        else {
            sourceAccount.setBalance(sourceAccount.getBalance() - amount);
        }
        accountRepository.save(sourceAccount);
        customer.getTransactionList().add(transaction);
        transactionRepository.save(transaction);
    }

    @Override
    public void internationalTransfer(Long sourceAccountNumber, Long destinationAccountNumber, double amount, String currency, String description, String partner, Customer customer, String targetIban, String swift) throws InsufficientFundsException, PartnerBankNotFoundException, InvalidIbanException {
        if (sourceAccountNumber == null || destinationAccountNumber == null || amount <= 0) {
            throw new IllegalArgumentException("Invalid input parameters.");
        }
        Account sourceAccount = accountRepository.findAccountById(sourceAccountNumber);

        if (sourceAccount.getBalance() < amount) {
            throw new InsufficientFundsException("Insufficient funds in source account.");
        }

        PartnerBank partnerBank = partnerBankRepository.findBySwiftCode(swift);
        if (partnerBank == null) {
            throw new PartnerBankNotFoundException("Partner bank not found with swift code: " + swift);
        }

        if (!isValidIban(targetIban)) {
            throw new InvalidIbanException("Invalid target account IBAN: " + targetIban);
        }

        double transferAmount = amount;
        if (!sourceAccount.getCurrency().equals(currency)) {
            transferAmount = convertAmount(currency, sourceAccount.getCurrency(), amount);
        }
        double totalFee = calculateTransferFee(transferAmount, partnerBank.getTransferFeeInEUR(), currency); // Assuming fee is in EUR

        if (sourceAccount.getBalance() < transferAmount + totalFee) {
            throw new InsufficientFundsException("Insufficient funds after considering transfer fee.");
        }



        sourceAccount.setBalance(sourceAccount.getBalance() - (transferAmount + totalFee));

        Transactions transaction = new Transactions();
        transaction.setAccount(sourceAccount);
        transaction.setAmount(transferAmount);
        transaction.setCurrency(currency);
        transaction.setCreationDate(Date.from(Instant.now()));
        transaction.setBookingDate(Date.from(Instant.now().plusSeconds(120)));
        transaction.setPartnerName(partner);
        transaction.setPartnerAccountNumb(destinationAccountNumber);
        transaction.setDescription(description);
        transaction.setStatus(TransactionStatus.PROCESSED);
        transaction.setCustomer(customer);

        accountRepository.save(sourceAccount);
        customer.getTransactionList().add(transaction);
        transactionRepository.save(transaction);
    }

    @Override
    public void valueDatedTransfer(long sourceAccountNumber, long toAccount, double amount, String currency, Customer customer) throws InsufficientFundsException {
        Account sourceAccount = accountRepository.findAccountById(sourceAccountNumber);
        if (sourceAccount == null || toAccount == 0 || amount <= 0) {
            throw new IllegalArgumentException("Invalid arguments");
        }
        if (sourceAccount.getBalance() < amount) {
            throw new InsufficientFundsException("Insufficient funds");
        }

        Transactions transaction = new Transactions();
        transaction.setAccount(sourceAccount);
        transaction.setPartnerAccountNumb(toAccount);
        transaction.setAmount(amount);
        transaction.setCustomer(customer);


        double amountToTransfer;
        if(!transaction.getCurrency().equals(sourceAccount.getCurrency())){
            amountToTransfer = convertAmount(currency, sourceAccount.getCurrency(), amount);
            sourceAccount.setBalance(sourceAccount.getBalance() - amountToTransfer);
        }
        else {
            sourceAccount.setBalance(sourceAccount.getBalance() - amount);
        }
        customer.getTransactionList().add(transaction);
        accountRepository.save(sourceAccount);
        transactionRepository.save(transaction);
    }

    private double convertAmount(String sourceCurrency, String targetCurrency, double amount) {
        ExchangeRate exchangeRate = exchangeRateRepository.findByBaseCurrencyAndTargetCurrency(sourceCurrency, targetCurrency);
        if (exchangeRate == null) {
            throw new IllegalArgumentException("Exchange rate not found for currencies: " + sourceCurrency + " and " + targetCurrency);
        }

        if (sourceCurrency == null || targetCurrency == null || amount < 0) {
            throw new IllegalArgumentException("Invalid input parameters: sourceCurrency, targetCurrency, or amount cannot be null or negative.");
        }

        double exchangeRateDouble = exchangeRate.getRate();
        return amount * exchangeRateDouble;
    }

    private double calculateTransferFee(double transferAmount, double partnerBankFeeInEUR, String currency) {
        // Validate input parameters
        if (transferAmount < 0 || partnerBankFeeInEUR < 0) {
            throw new IllegalArgumentException("Invalid input parameters: transferAmount or partnerBankFeeInEUR cannot be negative.");
        }

        double amountInEUR = convertAmount(currency, "EUR", transferAmount);

        double transferFee = amountInEUR * partnerBankFeeInEUR / 100;

        if (!currency.equals("EUR")) {
            transferFee = convertAmount(currency, "EUR", transferFee);
        }

        return transferFee;
    }

    private boolean isValidIban(String iban) {
        IbanCodes ibanCode = ibanCodeRepository.findByIban(iban);
        return ibanCode != null;
    }
}
