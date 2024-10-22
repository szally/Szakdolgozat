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
import java.util.List;
import java.util.Optional;

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
    GamificationService gamificationService;

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public void transferBetweenOwnAccounts(Long sourceAccountNumber, Long destinationAccountNumber, double amount,  String currency, String description, Customer customer) throws InsufficientFundsException {
        Account sourceAccount = accountRepository.findAccountById(sourceAccountNumber);
        Account destinationAccount = accountRepository.findAccountById(destinationAccountNumber);
        Transactions transaction = new Transactions();

        if (sourceAccount == null) {
            throw new RuntimeException("Source account not found!");
        }

        transaction.setAccount(sourceAccount);

        transaction.setAmount(amount);
        transaction.setCurrency(currency);
        transaction.setCreationDate(Date.from(Instant.now()));
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

        if (sourceAccount.getBalance() <= 0) {
            transaction.setStatus(TransactionStatus.FAILED);
            throw new InsufficientFundsException("Insufficient funds in source account");
        }

        if(destinationAccount.getType().equals(AccountType.SAVING)){
            gamificationService.updatePointsForSavings(customer, amount, currency);
        }

        accountRepository.save(sourceAccount);
        accountRepository.save(destinationAccount);

        customer.getTransactionList().add(transaction);
        transactionRepository.save(transaction);
        customerRepository.save(customer);
    }

    @Override
    public void domesticTransfer(Long sourceAccountNumber, Long destinationAccountNumber, double amount, String currency, String description, String partner, Customer customer) throws InsufficientFundsException {
        List<Account> accountList = accountRepository.findAll();
        Transactions transaction = new Transactions();
        if (sourceAccountNumber == null || destinationAccountNumber == null || amount <= 0) {
            throw new IllegalArgumentException("Invalid input parameters.");
        }
        Account sourceAccount = accountRepository.findAccountById(sourceAccountNumber);


        transaction.setAccount(sourceAccount);
        transaction.setAmount(amount);
        transaction.setCurrency(currency);
        transaction.setCreationDate(Date.from(Instant.now()));
        transaction.setPartnerName(partner);
        transaction.setPartnerAccountNumb(destinationAccountNumber);
        transaction.setDescription(description);
        transaction.setStatus(TransactionStatus.PROCESSED);
        transaction.setCustomer(customer);

        if (sourceAccount.getBalance() <= 0) {
            transaction.setStatus(TransactionStatus.FAILED);
            throw new InsufficientFundsException("Insufficient funds in source account");
        }

        double amountToTransfer;

        if(!transaction.getCurrency().equals(sourceAccount.getCurrency())){
            amountToTransfer = convertAmount(currency, sourceAccount.getCurrency(), amount);
            sourceAccount.setBalance(sourceAccount.getBalance() - amountToTransfer);
        }
        else {
            sourceAccount.setBalance(sourceAccount.getBalance() - amount);
        }

        Optional<Account> optionalDestinationAccount = Optional.ofNullable(accountRepository.findAccountById(destinationAccountNumber));
        for(Account account : accountList){
            optionalDestinationAccount.ifPresent(destinationAccount ->{
                if(destinationAccount.getId().equals(account.getId())){
                    if( destinationAccount.getCurrency().equals(currency)){
                        destinationAccount.setBalance(destinationAccount.getBalance() + amount);
                    }
                    else{
                        destinationAccount.setBalance(destinationAccount.getBalance() + convertAmount(currency, destinationAccount.getCurrency(), amount));
                    }
                }
            });


        }


        accountRepository.save(sourceAccount);
        customer.getTransactionList().add(transaction);
        transactionRepository.save(transaction);
    }

    @Override
    public void internationalTransfer(Long sourceAccountNumber, Long destinationAccountNumber, double amount, String currency, String description, String partner, Customer customer, String targetIban, String swift) throws InsufficientFundsException, PartnerBankNotFoundException, InvalidIbanException {
        Transactions transaction = new Transactions();
        if (sourceAccountNumber == null || targetIban == null || amount <= 0) {
            throw new IllegalArgumentException("Invalid input parameters.");
        }
        Account sourceAccount = accountRepository.findAccountById(sourceAccountNumber);

        PartnerBank partnerBank = findPartnerBankBySwift(swift);
        if (partnerBank == null) {
            throw new PartnerBankNotFoundException("Partner bank not found with swift code: " + swift);
        }

        if (!sourceAccount.getCurrency().equals(currency)) {
            convertAmount(currency, sourceAccount.getCurrency(), amount);
        }

        double totalFee = calculateTransferFee(amount, partnerBank.getTransferFeeInEUR(), currency);

        if(!sourceAccount.getCurrency().equals("EUR")) {
            convertAmount("EUR", sourceAccount.getCurrency(), totalFee);
        }

        if (sourceAccount.getBalance() < totalFee) {
            transaction.setStatus(TransactionStatus.FAILED);
            throw new InsufficientFundsException("Insufficient funds after considering transfer fee.");
        }

        sourceAccount.setBalance(sourceAccount.getBalance() - totalFee);


        transaction.setAccount(sourceAccount);
        transaction.setAmount(amount);
        transaction.setCurrency(currency);
        transaction.setCreationDate(Date.from(Instant.now()));
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
    public void chequePayment(long sourceAccountNumber, long toAccount, double amount, String currency, Customer customer) throws InsufficientFundsException {

    }

    public double convertAmount(String sourceCurrency, String targetCurrency, double amount) {
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
        if (transferAmount < 0 || partnerBankFeeInEUR < 0) {
            throw new IllegalArgumentException("Invalid input parameters: transferAmount or partnerBankFeeInEUR cannot be negative.");
        }

        double amountInEUR = convertAmount(currency, "EUR", transferAmount);

        double transferFee = amountInEUR + partnerBankFeeInEUR;

        return transferFee;
    }

    public PartnerBank findPartnerBankBySwift(String swift){
        return partnerBankRepository.findBySwiftCode(swift);
    }
}
