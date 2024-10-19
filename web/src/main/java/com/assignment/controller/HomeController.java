package com.assignment.controller;

import com.assignment.model.AccountListModel;
import com.assignment.model.AccountModel;
import com.assignment.model.CardListModel;
import com.assignment.model.CardModel;
import com.assignment.repository.CustomerRepository;
import com.assignment.security.CustomerLoginDetailsService;
import com.assignment.service.AccountServiceImpl;
import com.assignment.service.CardServiceImpl;
import com.assignment.service.CustomerDetailsServiceImpl;
import com.assignment.transformer.AccountTransformer;
import com.assignment.transformer.CardTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class HomeController {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerLoginDetailsService customerLoginDetailsService;

    @Autowired
    CustomerDetailsServiceImpl customerDetailsService;

    @Autowired
    AccountServiceImpl accountService;

    @Autowired
    AccountTransformer accountTransformer;

    @Autowired
    CardServiceImpl cardService;

    @Autowired
    CardTransformer cardTransformer;

    public HomeController() {
    }


    @ModelAttribute("accountModel")
    public AccountModel getAccountModel(){
        return new AccountModel();
    }

    @ModelAttribute("accountListModel")
    public AccountListModel getAccountListModel() {
        return new AccountListModel(this.accountTransformer
                .transformAccountListToAccountModelList(this.accountService.getAccountDetails(customerDetailsService.findCustomerByUsername(customerLoginDetailsService.loadAuthenticatedUsername()))));
    }

    @ModelAttribute("cardModel")
    public CardModel getCardModel(){
        return new CardModel();
    }

    @ModelAttribute("cardListModel")
    public CardListModel getCardListModel() {
        return new CardListModel(this.cardTransformer
                .transformCardListToCardModelList(this.cardService.getCardDetails(customerDetailsService.findCustomerByUsername(customerLoginDetailsService.loadAuthenticatedUsername()))));
    }

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("customer", this.customerDetailsService.findCustomerByUsername(customerLoginDetailsService.loadAuthenticatedUsername()));
        model.addAttribute("customersAccount", this.accountService.getAccountDetails(customerDetailsService.findCustomerByUsername(customerLoginDetailsService.loadAuthenticatedUsername())));
        model.addAttribute("customersCards", this.cardService.getCardDetails(customerDetailsService.findCustomerByUsername(customerLoginDetailsService.loadAuthenticatedUsername())));
        return "home-page";
    }/*
    @GetMapping( {"/accounts"})
    private String showAccounts(Model model) {
        return "accounts";
    }*/
}
