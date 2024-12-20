package com.assignment.controller;

import com.assignment.domain.Customer;
import com.assignment.model.TransactionListModel;
import com.assignment.model.TransactionsModel;
import com.assignment.security.CustomerLoginDetailsService;
import com.assignment.service.CustomerDetailsServiceImpl;
import com.assignment.service.TransactionHistoryServiceImpl;
import com.assignment.transformer.TransactionTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TransactionHistoryController {

    @Autowired
    CustomerDetailsServiceImpl customerDetailsService;
    @Autowired
    CustomerLoginDetailsService customerLoginDetailsService;
    @Autowired
    TransactionHistoryServiceImpl transactionService;

    @GetMapping({"/transactions"})
    public String showTransactionHistory(Model model) {
        model.addAttribute("customer", this.customerDetailsService.findCustomerByUsername(customerLoginDetailsService.loadAuthenticatedUsername()));
        model.addAttribute("customersTransactions", this.transactionService.getTransactionHistory(customerDetailsService.findCustomerByUsername(customerLoginDetailsService.loadAuthenticatedUsername())));
        return "transaction-history";
    }
}
