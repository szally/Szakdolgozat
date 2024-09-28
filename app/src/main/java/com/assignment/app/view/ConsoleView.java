package com.assignment.app.view;

import com.assignment.domain.*;
import com.assignment.persistance.FileDataStore;
import com.assignment.service.AccountService;
import com.assignment.service.AccountServiceImpl;
import com.assignment.service.CardService;
import com.assignment.service.CardServiceImpl;

import java.util.List;
import java.util.Scanner;

import static com.assignment.domain.AccountAndCardStatus.ACTIVE;
import static com.assignment.domain.AccountAndCardStatus.TERMINATED;
import static com.assignment.domain.CustomerStatus.BLOCKED;

public class ConsoleView {

    Scanner sc = new Scanner(System.in);
    private final FileDataStore dataStore = new FileDataStore("data");
    private AccountService accountService = new AccountServiceImpl(dataStore);
    private CardServiceImpl cardService = new CardServiceImpl(dataStore);

    public Credentials loginView() {
        System.out.println("Welcome to LoremIpsum Bank\n");
        System.out.println("Please sign in!\nLogin name: ");
        Scanner sc = new Scanner(System.in);

        String username = sc.nextLine();

        System.out.println("Please give the password assigned to the username!\nPassword: ");

        String password = sc.nextLine();

        return new Credentials(username,password);

    }


    public void statisticsView(Customer customer){
        System.out.println("Hi " +customer.getName() +"! \n");
    }

    public int menuView(Customer customer){

        if (customer.getStatus().equals(BLOCKED)) {
            System.out.println("Your account has been blocked. For more information, " +
                    "please contact our customer support at testbank@testbank.hu or call +1-800-555-1212.");
            System.exit(0);
        }


        System.out.println("1 - Manage Account");
        System.out.println("2 - Manage Cards");
        System.out.println("3 - Change Password");
        System.out.println("4 - Quit");
        System.out.print("\n\nPlease choose a menu item: ");

        int choice;
        do {
            Scanner scanner = new Scanner(System.in);
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number between 1 and 5: ");
                scanner.next();
            }
            choice = scanner.nextInt();
        } while (choice < 1 || choice > 5);

        return choice;
    }




    public List<Account> listAllAccounts(Customer customer){
        List<Account> accounts = accountService.getAccountDetails(customer);
        System.out.println("\nList of all your accounts: \n");

        for (Account account : accounts) {
                System.out.println("\n-" + account.getId() +
                        "\n  Balance: " + account.getBalance() + " " + account.getCurrency());

        }

        return accounts;
    }

    public void viewOpenNewAccount(Customer customer) {

        System.out.println("Opening a new account for customer:");
        System.out.println("  Name: " + customer.getName());
        System.out.print("Please enter the currency you want to open this account: ");
        String selectedCurrency = sc.nextLine();

        Account newAccount = accountService.openNewAccount(customer, selectedCurrency);

        System.out.println("\nNew account details:");
        System.out.println("  Account number: " + newAccount.getId()); // Assuming getter for account number exists
        System.out.println("  Balance: " + newAccount.getBalance());
        System.out.println("  Currency: " + newAccount.getCurrency());
        System.out.println("  Opening date: " + newAccount.getOpeningDate());
        System.out.println("  Status: " + newAccount.getStatus());

        listAllAccounts(customer);
    }

    public void blockAccView(Customer customer){
        listAllAccounts(customer);
        System.out.print("Enter account ID to block: ");
        long accountIdToBlock = sc.nextLong();
        Account accountToBlock = dataStore.getAccountById(accountIdToBlock);
        if (accountToBlock != null && !accountToBlock.getStatus().equals(BLOCKED)) {
            accountService.blockAccount(accountToBlock);
            System.out.println("Account blocked successfully.");
        } else {
            System.out.println("Account not found.");
        }
    }


    public void unBlockAccView(Customer customer){
        listAllAccounts(customer);
        System.out.print("Enter account ID to unblock: ");
        long accountIdToUnBlock = sc.nextLong();
        Account accountToUnBlock = dataStore.getAccountById(accountIdToUnBlock);
        if (accountToUnBlock != null && accountToUnBlock.getStatus().equals(BLOCKED)) {
            accountService.unBlockAccount(accountToUnBlock);
            System.out.println("Account unblocked successfully.");
        } else {
            System.out.println("Account not found.");
        }
    }

    public void closeAccountView(Customer customer){
        listAllAccounts(customer);
        System.out.print("Enter account ID to close: ");
        long accountIdToClose = sc.nextLong();
        Account accountToClose = dataStore.getAccountById(accountIdToClose);
        if (accountToClose != null && !accountToClose.getStatus().equals(TERMINATED)) {
            accountService.closeAccount(accountToClose);
            System.out.println("Account closed successfully.");
        } else {
            System.out.println("Account not found.");
        }
    }


    public void changePin(Customer customer) {
        dataStore.loadData();
        System.out.print("Enter card ID to change pin: ");
        long changePin = sc.nextLong();
        Card cardToChangePin = dataStore.getCardById(changePin);
        System.out.print("Set a PIN code ");
        int pin = sc.nextInt();
        cardService.updateCardDetails(customer, pin, cardToChangePin);
        listCards(customer);
    }

    public void listCards(Customer customer) {
        List<Card> cards = cardService.getCardDetails(customer);
        if (cards.isEmpty()) {
            System.out.println("You don't have any cards.");
        } else {
            for (Card card : cards) {
                System.out.println("Card ID: " + card.getId());
                System.out.println("Type: " + card.getType());
                System.out.println("Expiry date: " + card.getExpiryDate());
                System.out.println("Status: " + card.getStatus());
                System.out.println("PIN" + card.getPinCode());
            }
        }
    }

    public void requestNewCard(Customer customer) {
        dataStore.loadData();
        System.out.print("Enter the card type (DEBIT/CREDIT): ");
        String type = sc.next().toUpperCase();
        System.out.print("Set a PIN code ");
        int pin = sc.nextInt();
        Card newCard = cardService.requestNewCard(customer, customer.getName(), type, pin);
        System.out.println("New card request successful: " + newCard);
        listCards(customer);
    }//NEM JÓ!

    public void blockCard(Customer customer) {
        dataStore.loadData();
        listCards(customer);
        System.out.print("Enter the card ID to block: ");
        long cardId = sc.nextLong();
        Card card = dataStore.getCardById(cardId);
        cardService.blockCard(card);
        listCards(customer);
    }

    public void terminateCard(Customer customer) {
        dataStore.loadData();
        listCards(customer);
        System.out.print("Enter the card ID to terminate: ");
        long cardId = sc.nextLong();
        Card card = dataStore.getCardById(cardId);
        cardService.terminateCard(card);
        listCards(customer);
    }

}
