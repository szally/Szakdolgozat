package com.assignment.app;

import com.assignment.app.view.ConsoleView;
import com.assignment.domain.Customer;
import com.assignment.repository.AccountRepository;
import com.assignment.repository.CardRepositroy;
import com.assignment.repository.CustomerRepository;
import com.assignment.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Application implements CommandLineRunner {
    private final ConsoleView consoleView = new ConsoleView();

    public Scanner scanner = new Scanner(System.in);

    private Customer loggedInCustomer = null;

    @Autowired
    AuthencticationServiceImpl authencticationService;
    @Autowired
    private AccountServiceImpl accountService;
    @Autowired
    CardServiceImpl cardService;
    @Autowired
    CustomerDetailsServiceImpl customerDetailsService;
    @Autowired
    TransactionHistoryServiceImpl transactionHistoryService;
    @Autowired
    TransferServiceImpl transferService;

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    CardRepositroy cardRepositroy;

    @Override
    public void run(String... args) throws Exception {
        loggedInCustomer = authencticationService.login(consoleView.loginView());
        consoleView.statisticsView(loggedInCustomer);
        boolean quit = true;

        while (quit) {
            int item = consoleView.menuView(loggedInCustomer);
            authencticationService.setNewCustomerToActive(loggedInCustomer);
            switch (item){
                case 1:
                    manageAccount(loggedInCustomer);
                    break;
                case 2:
                    manageCards(loggedInCustomer);
                    break;
                case 3:
                    manageCustomerDetails(loggedInCustomer);
                    break;
                case 4:
                    manageTransactions(loggedInCustomer);
                    break;
                case 5:
                    transfer(loggedInCustomer);
                    break;
            }
        }


    }
    public void manageAccount(Customer customer) {
        System.out.println("\nAccount Management:");
        System.out.println("1 - List all accounts");
        System.out.println("2 - Open new account");
        System.out.println("3. Block Account");
        System.out.println("4. Unblock Account");
        System.out.println("5. Close Account");
        System.out.println("6. Back");

        int choice;
        do {
            System.out.print("\nPlease choose an option: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number between 1 and 6: ");
                scanner.next();
            }
            choice = scanner.nextInt();
        } while (choice < 1 || choice > 6);

        switch (choice) {
            case 1:
                consoleView.listAllAccounts(loggedInCustomer,accountService);
                break;
           case 2:
                consoleView.viewOpenNewAccount(loggedInCustomer, accountService);
                break;
            case 3:
                consoleView.blockAccView(loggedInCustomer, accountService, accountRepository);
                break;
            case 4:
                consoleView.unBlockAccView(loggedInCustomer, accountService, accountRepository);
                break;
            case 5:
                consoleView.closeAccountView(loggedInCustomer, accountService, accountRepository);
                break;
            case 6:
                // Do nothing, user wants to go back to main menu
                break;
        }
    }

    public void manageCards(Customer loggedInCustomer) {
        while (true) {
            System.out.println("\nCard Management:");
            System.out.println("1. List my cards");
            System.out.println("2. Request a new card");
            System.out.println("3. Block a card");
            System.out.println("4. Unblock a card");
            System.out.println("5. Terminate card");
            System.out.println("6. Change PIN");
            System.out.println("7. Back");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    consoleView.listCards(loggedInCustomer, cardService);
                    break;
                case 2:
                    consoleView.requestNewCard(loggedInCustomer, cardService, cardRepositroy);
                    break;
                case 3:
                    consoleView.blockCard(loggedInCustomer, cardService, cardRepositroy);
                    break;
                case 4:consoleView.unblockCard(loggedInCustomer,cardService,cardRepositroy);
                    break;
                case 5:
                    consoleView.terminateCard(loggedInCustomer, cardService, cardRepositroy);
                    break;
                case 6:
                    consoleView.changePin(loggedInCustomer, cardService, cardRepositroy);
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    public void manageCustomerDetails(Customer loggedInCustomer) {
        while (true) {
            System.out.println("\nCustomerDetails Management:");
            System.out.println("1. Change Personal data");
            System.out.println("2. Change Password");
            System.out.println("3. Back");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    consoleView.viewUpdateCustomerDetails(loggedInCustomer, customerDetailsService);
                    break;
                case 2:
                    consoleView.viewChangePassword(loggedInCustomer, customerDetailsService);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    public void manageTransactions(Customer loggedInCustomer) {
        while (true) {
            System.out.println("\nTH Management:");
            System.out.println("1. Transaction History");
            System.out.println("2. Download TH in JSON");
            System.out.println("3. Download TH in CSV");
            System.out.println("4. Back");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    consoleView.displayTransactionHistory(loggedInCustomer, transactionHistoryService);
                    break;
                case 2:
                    consoleView.viewDownloadTransactionHistoryInJSON(loggedInCustomer,transactionHistoryService);
                    break;
                case 3:
                    consoleView.viewDownloadTransactionHistoryInCSV(loggedInCustomer, transactionHistoryService);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    public void transfer(Customer loggedInCustomer) {
        while (true) {
            System.out.println("\nTransfer Management:");
            System.out.println("1. Transfer Between Own Account");
            System.out.println("2. Domestic Transfer");
            System.out.println("3. Download TH in CSV");
            System.out.println("4. Back");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    consoleView.viewTransferBetweenOwnAccounts(loggedInCustomer, transferService, accountService);
                    break;
                case 2:
                    consoleView.viewDomesticTransfer(loggedInCustomer,transferService,accountService);
                    break;
                case 3:

                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }
}
