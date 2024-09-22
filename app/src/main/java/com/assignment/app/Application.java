package com.assignment.app;

import com.assignment.app.view.ConsoleView;
import com.assignment.domain.Account;
import com.assignment.domain.Customer;
import com.assignment.persistance.FileDataStore;
import com.assignment.service.AuthencticationServiceImpl;
import com.assignment.service.AuthenticationService;
import com.assignment.service.CustomerDetailsServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Application implements CommandLineRunner {

    private final FileDataStore dataStore = new FileDataStore("data");
    private final AuthencticationServiceImpl authencticationService = new AuthencticationServiceImpl(dataStore);
    private final CustomerDetailsServiceImpl customerDetailsService = new CustomerDetailsServiceImpl(dataStore);
    private final ConsoleView consoleView = new ConsoleView();

    private Customer loggedInCustomer = null;

    @Override
    public void run(String... args) throws Exception {
        dataStore.loadData();
        loggedInCustomer = authencticationService.login(consoleView.loginView());
        consoleView.statisticsView(loggedInCustomer);

        boolean quit = true;

        while (quit) {
            int item = consoleView.menuView(loggedInCustomer);
            authencticationService.setNewCustomerToActive(loggedInCustomer);
            switch (item){
                case 1:
                   /* List<Account> accounts = customerDetailsService.viewAccountSummary(loggedInCustomer);
                    consoleView.listAllAccounts(accounts); // Assuming you have a method to print accounts*/
                    break;
            }
        }


    }
}
