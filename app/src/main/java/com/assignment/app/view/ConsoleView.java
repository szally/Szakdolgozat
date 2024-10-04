package com.assignment.app.view;

import com.assignment.domain.*;
import com.assignment.repository.AccountRepository;
import com.assignment.repository.CardRepositroy;
import com.assignment.service.*;
import com.assignment.service.exceptions.InvalidPasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static com.assignment.domain.AccountAndCardStatus.ACTIVE;
import static com.assignment.domain.AccountAndCardStatus.TERMINATED;
import static com.assignment.domain.CustomerStatus.BLOCKED;


@Component
public class ConsoleView {

    Scanner sc = new Scanner(System.in);

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
        System.out.println("3 - Manage Customer Details");
        System.out.println("4 - Transaction History");
        System.out.println("5 - Transfer Options");
        System.out.println("6 - Quit");
        System.out.print("\n\nPlease choose a menu item: ");

        int choice;
        do {
            Scanner scanner = new Scanner(System.in);
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number between 1 and 6: ");
                scanner.next();
            }
            choice = scanner.nextInt();
        } while (choice < 1 || choice > 6);

        return choice;
    }




    public List<Account> listAllAccounts(Customer customer, AccountServiceImpl accountService){
        List<Account> accounts = accountService.getAccountDetails(customer);
        System.out.println("\nList of all your accounts: \n");

        for (Account account : accounts) {
            System.out.println("\n-" + account.getId() +
                    "\n  Balance: " + account.getBalance() + " " + account.getCurrency()
                    +
                    "\n  Status: " + account.getStatus());

        }

        return accounts;
    }
    public void viewOpenNewAccount(Customer customer,  AccountServiceImpl accountService) {

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

        listAllAccounts(customer, accountService);
    }

    public void blockAccView(Customer customer,  AccountServiceImpl accountService, AccountRepository accountRepository){
        listAllAccounts(customer, accountService);
        System.out.print("Enter account ID to block: ");
        long accountIdToBlock = sc.nextLong();
        Account accountToBlock = accountRepository.getAccountById(accountIdToBlock);
        if (accountToBlock != null && !accountToBlock.getStatus().equals(BLOCKED)) {
            accountService.blockAccount(accountToBlock);
            System.out.println("Account blocked successfully.");
        } else {
            System.out.println("Account not found.");
        }
    }


    public void unBlockAccView(Customer customer, AccountServiceImpl accountService, AccountRepository accountRepository){
        listAllAccounts(customer, accountService);
        System.out.print("Enter account ID to unblock: ");
        long accountIdToUnBlock = sc.nextLong();
        Account accountToUnBlock = accountRepository.getAccountById(accountIdToUnBlock);
        if (accountToUnBlock != null) {
            accountService.unBlockAccount(accountToUnBlock);
            System.out.println("Account unblocked successfully.");
        } else {
            System.out.println("Account not found.");
        }
    }

    public void closeAccountView(Customer customer, AccountServiceImpl accountService, AccountRepository accountRepository){
        listAllAccounts(customer, accountService);
        System.out.print("Enter account ID to close: ");
        long accountIdToClose = sc.nextLong();
        Account accountToClose = accountRepository.getAccountById(accountIdToClose);
        if (accountToClose != null && !accountToClose.getStatus().equals(TERMINATED)) {
            accountService.closeAccount(accountToClose);
            System.out.println("Account closed successfully.");
        } else {
            System.out.println("Account not found.");
        }
    }


    public void changePin(Customer customer, CardServiceImpl cardService, CardRepositroy cardRepositroy) {
        System.out.print("Enter card ID to change pin: ");
        long changePin = sc.nextLong();
        Card cardToChangePin = cardRepositroy.getCardById(changePin);
        System.out.print("Set a PIN code ");
        int pin = sc.nextInt();
        cardService.updateCardDetails(customer, pin, cardToChangePin);
        listCards(customer, cardService);
    }

    public void listCards(Customer customer, CardServiceImpl cardService) {
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

    public void requestNewCard(Customer customer, CardServiceImpl cardService, CardRepositroy cardRepositroy) {
        System.out.print("Enter the card type (DEBIT/CREDIT): ");
        String type = sc.next().toUpperCase();
        System.out.print("Set a PIN code (4 digits): ");
        int pin = sc.nextInt();

        // Validate PIN format (optional)
        if (String.valueOf(pin).length() != 4) {
            System.out.println("Invalid PIN format. Please enter a 4-digit PIN.");
            return;
        }

        Card newCard = cardService.requestNewCard(customer, customer.getName(), type, pin);
       // cardService.saveCard(newCard); // Persist the new card
        System.out.println("New card request successful: " + newCard);
        listCards(customer, cardService);
    }

    public void blockCard(Customer customer, CardServiceImpl cardService, CardRepositroy cardRepositroy) {
        listCards(customer, cardService);
        System.out.print("Enter the card ID to block: ");
        long cardId = sc.nextLong();
        Card card = cardRepositroy.getCardById(cardId);
        cardService.blockCard(card);
        listCards(customer, cardService);
    }

    public void unblockCard(Customer customer, CardServiceImpl cardService, CardRepositroy cardRepositroy) {
        listCards(customer, cardService);
        System.out.print("Enter the card ID to block: ");
        long cardId = sc.nextLong();
        Card card = cardRepositroy.getCardById(cardId);
        cardService.unBlockCard(card);
        listCards(customer, cardService);
    }

    public void terminateCard(Customer customer, CardServiceImpl cardService, CardRepositroy cardRepositroy) {
        listCards(customer, cardService);
        System.out.print("Enter the card ID to terminate: ");
        long cardId = sc.nextLong();
        Card card = cardRepositroy.getCardById(cardId);
        cardService.terminateCard(card);
        listCards(customer, cardService);
    }

    public void viewUpdateCustomerDetails(Customer customer, CustomerDetailsServiceImpl customerDetailsService) {
        System.out.println("Updating customer details:");
        System.out.println("  Current name: " + customer.getName());
        System.out.print("Enter new name: ");
        String newName = sc.nextLine();

        System.out.println("  Current ID card number: " + customer.getIdCardNumb());
        System.out.print("Enter new ID card number: ");
        String newIdCardNumb = sc.nextLine();

        System.out.println("  Current email: " + customer.getEmail());
        System.out.print("Enter new email: ");
        String newEmail = sc.nextLine();

        // Update the customer details using the provided information
        customer = customerDetailsService.updateCustomerDetails(customer, newName, newIdCardNumb, newEmail);

        System.out.println("\nUpdated customer details:");
        System.out.println("  Name: " + customer.getName());
        System.out.println("  ID card number: " + customer.getIdCardNumb());
        System.out.println("  Email: " + customer.getEmail());
    }
    public void viewChangePassword(Customer customer, CustomerDetailsServiceImpl customerDetailsService) {
        System.out.println("Changing password for customer:");
        System.out.println("  Current name: " + customer.getName());

        System.out.print("Enter new password: ");
        String newPassword = sc.nextLine();

        try {
            customer = customerDetailsService.changePassword(customer, newPassword);
            System.out.println("Password changed successfully.");
        } catch (InvalidPasswordException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void displayTransactionHistory(Customer customer, TransactionHistoryServiceImpl transactionHistoryService) {
        List<Transactions> transactions = transactionHistoryService.getTransactionHistory(customer);
        if (transactions.isEmpty()) {
            System.out.println("No transactions found for this customer.");
            return;
        }

        System.out.println("Transaction History:");
        System.out.println("-------------------");
        for (Transactions transaction : transactions) {
            System.out.println(formatTransaction(transaction));
        }
    }

    private String formatTransaction(Transactions transaction) {
        return String.format(
                "ID: %d | Amount: %.2f %s | Date: %s | Status: %s | Partner: %s | Account: %s",
                transaction.getId(),
                transaction.getAmount(),
                transaction.getCurrency(),
                transaction.getBookingDate().toGMTString(),
                transaction.getStatus(),
                transaction.getPartnerName(),
                transaction.getAccount().getId() // Assuming Account has getAccountNumber()
        );
    }

    public void viewDownloadTransactionHistoryInJSON(Customer customer, TransactionHistoryServiceImpl transactionHistoryService) {
        List<Transactions> transactions = transactionHistoryService.getTransactionHistory(customer);
        if (transactions.isEmpty()) {
            System.out.println("No transactions found for this customer.");
            return;
        }
        String filePath = "exports/TransactionHistory.json";

        transactionHistoryService.downloadTransactionHistoryInJSON(transactions, filePath);
        System.out.println("Transaction history downloaded as JSON to " + filePath);
    }

    public void viewDownloadTransactionHistoryInCSV(Customer customer, TransactionHistoryServiceImpl transactionHistoryService) {
        List<Transactions> transactions = transactionHistoryService.getTransactionHistory(customer);
        if (transactions.isEmpty()) {
            System.out.println("No transactions found for this customer.");
            return;
        }
        String filePath = "exports/TransactionHistory.csv";

        transactionHistoryService.writeToCSV(transactions, filePath);
        System.out.println("Transaction history downloaded as CSV to " + filePath);
    }

    public void viewTransferBetweenOwnAccounts(Customer customer, TransferServiceImpl transferService, AccountServiceImpl accountService) {
        System.out.println("Transferring between own accounts:");

        // Get source account information
        System.out.println("  Source account:");
        listAllAccounts(customer,accountService);
        System.out.print("    Enter source account number: ");
        Long sourceAccountNumber = Long.parseLong(sc.nextLine());

        // Get destination account information
        System.out.println("  Destination account:");
        listAllAccounts(customer,accountService);
        System.out.print("    Enter destination account number: ");
        Long destinationAccountNumber = Long.parseLong(sc.nextLine());

        // Get transfer amount
        System.out.print("  Enter transfer amount: ");
        double amount = Double.parseDouble(sc.nextLine());

        System.out.print("  Enter transfer currency: ");
        String currency = sc.nextLine();

        // Get transaction details
        System.out.print("  Enter transaction description: ");
        String description = sc.nextLine();

        // Perform the transfer
        transferService.transferBetweenOwnAccounts(sourceAccountNumber, destinationAccountNumber, amount, currency, description, customer);

        // Display transfer confirmation
        System.out.println("\nTransfer completed successfully!");
        System.out.println("  Source account: " + sourceAccountNumber);
        System.out.println("  Destination account: " + destinationAccountNumber);
        System.out.println("  Amount transferred: " + amount);
        System.out.println("  Description: " + description);
    }

    public void viewDomesticTransfer(Customer customer, TransferServiceImpl transferService, AccountServiceImpl accountService) {
        System.out.println("Transferring between own accounts:");

        // Get source account information
        System.out.println("  Source account:");
        listAllAccounts(customer,accountService);
        System.out.print("    Enter source account number: ");
        Long sourceAccountNumber = Long.parseLong(sc.nextLine());

        // Get destination account information
        System.out.println("  Destination account:");
        System.out.print("    Enter partner account number: ");
        Long destinationAccountNumber = Long.parseLong(sc.nextLine());

        System.out.print("   Enter partner account number: ");
        String partnerName = sc.nextLine();

        // Get transfer amount
        System.out.print("  Enter transfer amount: ");
        double amount = Double.parseDouble(sc.nextLine());

        System.out.print("  Enter transfer curreny: ");
        String currency = sc.nextLine();

        // Get transaction details
        System.out.print("  Enter transaction description: ");
        String description = sc.nextLine();

        // Perform the transfer
        transferService.domesticTransfer(sourceAccountNumber, destinationAccountNumber, amount, currency, description, partnerName,customer);

        // Display transfer confirmation
        System.out.println("\nTransfer completed successfully!");
        System.out.println("  Source account: " + sourceAccountNumber);
        System.out.println("  Destination account: " + destinationAccountNumber);
        System.out.println("  Partner name: " + partnerName);
        System.out.println("  Amount transferred: " + amount);
        System.out.println("  Description: " + description);
    }


}
