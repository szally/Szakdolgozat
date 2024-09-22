package com.assignment.persistance;

import com.assignment.domain.Account;
import com.assignment.domain.Card;
import com.assignment.domain.Customer;
import com.assignment.domain.Transactions;
import com.assignment.persistance.dto.AccountDTO;
import com.assignment.persistance.dto.CardDTO;
import com.assignment.persistance.dto.CustomerDTO;
import com.assignment.persistance.dto.TransactionsDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class FileDataStore {
    private static final String CUSTOMER_FILE = "customer.json";
    private static final String ACCOUNT_FILE = "account.json";
    private static final String TRANSACTION_FILE = "transactions.json";
    private static final String CARD_FILE = "card.json";

    private final ObjectMapper objectMapper = new ObjectMapper();

    private List<Customer> customers;
    private List<Account> accounts;
    private List<Card> cards;
    private List<Transactions> transactions;

    private CustomerDTO[] customerDTO;
    private AccountDTO[] accountDTO;
    private CardDTO[] cardDTO;
    private TransactionsDTO[] transactionsDTO;

    private final String basePath;

    @Autowired
    public FileDataStore(@Value("${basePath}") String basePath) {

        this.basePath = basePath;

    }

    public void loadData() {
        objectMapper.findAndRegisterModules();

        cardDTO = readCards();
        accountDTO = readAccounts();
        transactionsDTO = readTransactions();
        customerDTO = readCustomers();

        cards = convertCards(cardDTO);
        accounts = convertAccounts(accountDTO);
        transactions = convertTransactions(transactionsDTO);
        customers = convertCustomer(customerDTO);
    }

    private String getPath(String fileName) {
        return basePath + File.separator + fileName;
    }

    public List<Card> getCards() {

        return cards;
    }
    public List<Account> getAccounts() {

        return accounts;
    }
    public List<Transactions> getTransactions() {

        return transactions;
    }
    public List<Customer> getCustomers() {

        return customers;
    }

    private CardDTO[] readCards() {
        try {
            return objectMapper.readValue(new File(getPath(CARD_FILE)),
                    CardDTO[].class);
        } catch (IOException e) {
            throw new RuntimeException("IO error happened while reading personal properties: " + e.getMessage(), e);
        }
    }

    private AccountDTO[] readAccounts() {
        try {
            return objectMapper.readValue(new File(getPath(ACCOUNT_FILE)),
                    AccountDTO[].class);
        } catch (IOException e) {
            throw new RuntimeException("IO error happened while reading personal properties: " + e.getMessage(), e);
        }
    }

    private TransactionsDTO[] readTransactions() {
        try {
            return objectMapper.readValue(new File(getPath(TRANSACTION_FILE)),
                    TransactionsDTO[].class);
        } catch (IOException e) {
            throw new RuntimeException("IO error happened while reading personal properties: " + e.getMessage(), e);
        }
    }
    private CustomerDTO[] readCustomers() {
        try {
            return objectMapper.readValue(new File(getPath(CUSTOMER_FILE)),
                    CustomerDTO[].class);
        } catch (IOException e) {
            throw new RuntimeException("IO error happened while reading personal properties: " + e.getMessage(), e);
        }
    }


    private List<Card> convertCards(CardDTO[] cards){

        List<Card> cardList = new ArrayList<>();

        for(CardDTO dto : cards){
            cardList.add(new Card(
                    dto.getId(),
                    dto.getCustomerName(),
                    dto.getExpiryDate(),
                    dto.getStatus(),
                    dto.getPinCode()
            ));
        }

        return cardList;
    }


    private List<Account> convertAccounts(AccountDTO[] accounts){

        List<Account> accountList = new ArrayList<>();

        for(AccountDTO dto : accounts){
            //Card card = getCardById(dto.getCard());
            accountList.add(new Account(
                    dto.getId(),
                    dto.getBalance(),
                    dto.getCurrency(),
                    dto.getOpeningDate(),
                    dto.getStatus(),
                    getCardById(dto.getCard())
            ));
        }

        return accountList;
    }

    private List<Transactions> convertTransactions(TransactionsDTO[] transactions){

        List<Transactions> transactionsList = new ArrayList<>();

        for(TransactionsDTO dto : transactions){
            Account account = getAccountById(dto.getAccount());
            transactionsList.add(new Transactions(
                    dto.getId(),
                    dto.getAmount(),
                    dto.getCurrency(),
                    dto.getCreationDate(),
                    dto.getBookingDate(),
                    dto.getPartnerName(),
                    dto.getPartnerAccountNumb(),
                    dto.getDescription(),
                    dto.getIsExpense(),
                    dto.getStatus(),
                    account
            ));
        }
        return transactionsList;
    }


    private List<Customer> convertCustomer(CustomerDTO[] customers){

        List<Customer> customerList = new ArrayList<>();

        for(CustomerDTO dto : customers){
            List<Account> accountList = new ArrayList<>();
            List<Card> cardList = new ArrayList<>();
            List<Transactions> transactionsList = new ArrayList<>();
            for (Long id : dto.getAccounts()) {
                accountList.add(
                        getAccountById(id)
                );
            }
            for (Long id : dto.getCards()) {
                cardList.add(
                        getCardById(id)
                );
            }
            for (Long id : dto.getTransactionList()) {
                transactionsList.add(
                        getTransactionById(id)
                );
            }
            customerList.add(new Customer(
                    dto.getId(),
                    dto.getCredentials(),
                    dto.getName(),
                    dto.getBirthPlace(),
                    dto.getBirthDate(),
                    dto.getMothersName(),
                    dto.getIdCardNumb(),
                    dto.getTaxNumb(),
                    dto.getEmail(),
                    dto.getStatus(),
                    accountList,
                    cardList,
                    transactionsList
            ));
        }
        return customerList;
    }


    private Card getCardById(Long cardid) {
        Card searchedCard = null;
        for (Card card : getCards()) {
            if (card.getId().equals(cardid)) {
                searchedCard = card;
                break;
            }

        }
        return searchedCard;
    }

    private Account getAccountById(Long accountId) {
        Account searchedAccount = null;
        for (Account account : accounts) {
            if (account.getId().equals(accountId)) {
                searchedAccount = account;
                break;
            }

        }
        return searchedAccount;
    }

    private Transactions getTransactionById(Long transactionId) {
        Transactions searchedTransaction = null;
        for (Transactions transaction : transactions) {
            if (transaction.getId().equals(transactionId)) {
                searchedTransaction = transaction;
                break;
            }

        }
        return searchedTransaction;
    }

    public Customer getCustomerByCredentials(String username) {
        Customer searchedCustomer = null;
        for (Customer customer : customers){
            if(customer.getCredentials().getUsername().equals(username)){
                searchedCustomer = customer;
                break;
            }
        }
        return searchedCustomer;
    }


    public void writeCardJson() {
        try (FileWriter file = new FileWriter("data\\card.json", StandardCharsets.UTF_8)) {
            file.write("[\n");

            for (int i = 0; i < cards.size(); i++) {
                file.write("\t{\n");
                file.write("\t\"id\": " + cards.get(i).getId() + ",\n");
                file.write("\t\"customerName\": \"" + cards.get(i).getCustomerName() + "\",\n");
                file.write("\t\"expiryDate\": \"" + cards.get(i).getExpiryDate() + "\",\n");
                file.write("\t\"type\": \"" + cards.get(i).getType() + "\",\n");
                file.write("\t\"status\": " + cards.get(i).getStatus() + ",\n");
                file.write("\t}\n");

                if (!(i == cards.size() - 1)) {
                    file.write(",\n");
                }
            }
            file.write("\n\t]\n");
            file.flush();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void writeAccountJson() {
        try (FileWriter file = new FileWriter("data\\account.json", StandardCharsets.UTF_8)) {
            file.write("[\n");

            for (int i = 0; i < accounts.size(); i++) {
                file.write("\t{\n");
                file.write("\t\"id\": " + accounts.get(i).getId() + ",\n");
                file.write("\t\"balance\": \"" + accounts.get(i).getBalance() + "\",\n");
                file.write("\t\"currency\": \"" + accounts.get(i).getCurrency() + "\",\n");
                file.write("\t\"openingDate\": \"" + accounts.get(i).getOpeningDate() + "\",\n");
                file.write("\t\"status\": " + accounts.get(i).getStatus() + ",\n");
                file.write("\t\"card\": " + accounts.get(i).getCard() + ",\n");
                file.write("\t}\n");

                if (!(i == accounts.size() - 1)) {
                    file.write(",\n");
                }
            }
            file.write("\n\t]\n");
            file.flush();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeTransactionJson() {
        try (FileWriter file = new FileWriter("data\\transactions.json", StandardCharsets.UTF_8)) {
            file.write("[\n");

            for (int i = 0; i < transactions.size(); i++) {
                file.write("\t{\n");
                file.write("\t\"id\": " + transactions.get(i).getId() + ",\n");
                file.write("\t\"amount\": \"" + transactions.get(i).getAmount() + "\",\n");
                file.write("\t\"currency\": \"" + transactions.get(i).getCurrency() + "\",\n");
                file.write("\t\"creationDate\": \"" + transactions.get(i).getCreationDate() + "\",\n");
                file.write("\t\"bookingDate\": " + transactions.get(i).getBookingDate() + ",\n");
                file.write("\t\"partnerName\": " + transactions.get(i).getPartnerName() + ",\n");
                file.write("\t\"partnerAccountNumb\": " + transactions.get(i).getPartnerAccountNumb() + ",\n");
                file.write("\t\"description\": " + transactions.get(i).getDescription() + ",\n");
                file.write("\t\"isExpense\": " + transactions.get(i).getIsExpense() + ",\n");
                file.write("\t\"status\": " + transactions.get(i).getStatus() + ",\n");
                file.write("\t\"account\": " + transactions.get(i).getAccount() + ",\n");
                file.write("\t}\n");

                if (!(i == accounts.size() - 1)) {
                    file.write(",\n");
                }
            }
            file.write("\n\t]\n");
            file.flush();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeCustomersJson() {
        try (FileWriter file = new FileWriter("data\\customer.json", StandardCharsets.UTF_8)) {
            file.write("[\n");

            for (int i = 0; i < customers.size(); i++) {
                file.write("\t{\n");
                file.write("\t\"id\": " + customers.get(i).getId() + ",\n");
                file.write("\t\"\"credentials\": \t{\n" );
                file.write("\t\"username\": \"" + customers.get(i).getCredentials().getUsername() + "\",\n");
                file.write("\t\"password\": \"" + customers.get(i).getCredentials().getPassword() + "\",\n");
                file.write("\t}");
                file.write(",\n");
                file.write("\t\"name\": \"" + customers.get(i).getName() + "\",\n");
                file.write("\t\"birthPlace\": " + customers.get(i).getBirthPlace()+ ",\n");
                file.write("\t\"birthDate\": " + customers.get(i).getBirthDate()+ ",\n");
                file.write("\t\"mothersName\": " + customers.get(i).getMothersName() + ",\n");
                file.write("\t\"idCardNumb\": " + customers.get(i).getIdCardNumb()+ ",\n");
                file.write("\t\"taxNumb\": " + customers.get(i).getTaxNumb()+ ",\n");
                file.write("\t\"email\": " + customers.get(i).getEmail()+ ",\n");
                file.write("\t\"status\": " + customers.get(i).getStatus() + ",\n");
                file.write("\t\"accounts\": [\n");

                if (customers.get(i).getAccounts().size() > 0) {
                    for (int j = 0; j < customers.get(i).getAccounts().size(); j++) {
                        file.write(String.valueOf( customers.get(i).getAccounts().get(j).getId() ));
                        if (!(j == customers.get(i).getAccounts().size() - 1)) {
                            file.write(",\n");
                        }
                    }
                }
                file.write("\n\t]\n");
                file.write("\t}\n");
                file.write(",\n");

                file.write("\t\"transactionList\": [\n");

                if (customers.get(i).getTransactionList().size() > 0) {
                    for (int j = 0; j < customers.get(i).getTransactionList().size(); j++) {
                        file.write(String.valueOf( customers.get(i).getTransactionList().get(j).getId() ));
                        if (!(j == customers.get(i).getTransactionList().size() - 1)) {
                            file.write(",\n");
                        }
                    }
                }
                file.write("\n\t]\n");
                file.write("\t}\n");

                if (!(i == customers.size() - 1)) {
                    file.write(",\n");
                }
            }
            file.write("\n\t]\n");
            file.flush();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Customer findCustomerById(Long id) {
        Customer searchedCustomer = null;
        for (Customer customer : customers) {
            if (customer.getId().equals(id)) {
                searchedCustomer = customer;
                break;
            }

        }
        return searchedCustomer;
    }
}
