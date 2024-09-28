package com.assignment.app;

import com.assignment.app.view.ConsoleView;
import com.assignment.domain.Account;
import com.assignment.domain.Customer;
import com.assignment.repository.CustomerRepository;
import com.assignment.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class Application implements CommandLineRunner {
    private final ConsoleView consoleView = new ConsoleView();

    public Scanner scanner = new Scanner(System.in);

    private Customer loggedInCustomer = null;

    @Autowired
    AuthencticationServiceImpl authencticationService;

    @Autowired
    CustomerRepository customerRepository;
    @Override
    public void run(String... args) throws Exception {
        //dataStore.loadData();
        System.out.println(customerRepository.findAll());
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
                    //manageCards(loggedInCustomer);
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
                System.out.println("Invalid input. Please enter a number between 1 and 4: ");
                scanner.next();
            }
            choice = scanner.nextInt();
        } while (choice < 1 || choice > 4);

        switch (choice) {
            case 1:
                consoleView.listAllAccounts(loggedInCustomer);
                break;
/*            case 2:
                consoleView.viewOpenNewAccount(loggedInCustomer);
                break;
            case 3:
                consoleView.blockAccView(loggedInCustomer);
                break;
            case 4:
                consoleView.unBlockAccView(loggedInCustomer);
                break;
            case 5:
                consoleView.closeAccountView(loggedInCustomer);
                break;*/
            case 6:
                // Do nothing, user wants to go back to main menu
                break;
        }
    }

   /* public void manageCards(Customer loggedInCustomer) {
        while (true) {
            System.out.println("\nCard Management:");
            System.out.println("1. List my cards");
            System.out.println("2. Request a new card");
            System.out.println("3. Block a card");
            System.out.println("4. Terminate card");
            System.out.println("5. Change PIN");
            System.out.println("6. Back");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    consoleView.listCards(loggedInCustomer);
                    break;
                case 2:
                    consoleView.requestNewCard(loggedInCustomer);
                    break;
                case 3:
                    consoleView.blockCard(loggedInCustomer);
                    break;
                case 4:
                    consoleView.terminateCard(loggedInCustomer);
                    break;
                case 5:
                    consoleView.changePin(loggedInCustomer);
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }*/
}
