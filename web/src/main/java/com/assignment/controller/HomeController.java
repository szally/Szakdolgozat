package com.assignment.controller;

import com.assignment.security.CustomerLoginDetailsService;
import com.assignment.service.CardServiceImpl;
import com.assignment.service.CustomerDetailsServiceImpl;
import com.assignment.service.TransactionHistoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private CustomerLoginDetailsService customerLoginDetailsService;
    @Autowired
    CustomerDetailsServiceImpl customerDetailsService;
    @Autowired
    CardServiceImpl cardService;
    @Autowired
    TransactionHistoryServiceImpl transactionService;

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("customer", this.customerDetailsService.findCustomerByUsername(customerLoginDetailsService.loadAuthenticatedUsername()));
        model.addAttribute("customersCards", this.cardService.getCardDetails(customerDetailsService.findCustomerByUsername(customerLoginDetailsService.loadAuthenticatedUsername())));
        model.addAttribute("customersTransactions", this.transactionService.getTransactionHistory(customerDetailsService.findCustomerByUsername(customerLoginDetailsService.loadAuthenticatedUsername())));
        return "home";
    }
}
