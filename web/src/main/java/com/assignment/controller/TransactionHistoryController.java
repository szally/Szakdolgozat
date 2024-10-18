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

@Controller
public class TransactionHistoryController {


    @Autowired
    TransactionTransformer transactionTransformer;

    @Autowired
    CustomerDetailsServiceImpl customerDetailsService;

    @Autowired
    CustomerLoginDetailsService customerLoginDetailsService;

    @Autowired
    TransactionHistoryServiceImpl transactionService;


    @ModelAttribute("transactionModel")
    public TransactionsModel getTransactionModel(){
        return new TransactionsModel();
    }

    @ModelAttribute("transactionListModel")
    public TransactionListModel getAccountListModel() {
        return new TransactionListModel(this.transactionTransformer
                .transformTransactionsListToTransactionsModelList(this.transactionService.getTransactionHistory(customerDetailsService.findCustomerByUsername(customerLoginDetailsService.loadAuthenticatedUsername()))));
    }

    @GetMapping({"/transactions"})
    public String showTransactionHistory(Model model) {
        model.addAttribute("customersAccount", getAccountListModel());
        model.addAttribute("account", this.transactionService.findAllTransactions());
        return "player-games-page";
    }
}
