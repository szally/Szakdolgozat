package com.assignment.app.view;

import com.assignment.domain.Account;
import com.assignment.domain.Credentials;
import com.assignment.domain.Customer;
import com.assignment.domain.CustomerStatus;

import java.util.List;
import java.util.Scanner;

import static com.assignment.domain.AccountAndCardStatus.ACTIVE;
import static com.assignment.domain.CustomerStatus.NEW;
import static com.assignment.domain.CustomerStatus.BLOCKED;

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

        System.out.println("1 - Check Balance");
        System.out.println("2 - Transfer Funds");
        System.out.println("3 - View Transactions");
        System.out.println("4 - Change Password");
        System.out.println("5 - Quit");
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

   /* public List<Account> listAllAccounts(List<Account> accounts){
        System.out.println("\nList of all your accounts: \n");

        for (Account account : accounts) {
            if(account.getStatus().equals(ACTIVE)) {
                System.out.println("\n-" + account.getId() +
                        "\n  Balance: " + account.getBalance() + " " + account.getCurrency());
            }
        }

        return accounts;
    }*/
}
