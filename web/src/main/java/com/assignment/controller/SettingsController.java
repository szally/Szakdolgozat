package com.assignment.controller;

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

@Controller
public class SettingsController {

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
    @GetMapping("/settings")
    public String home(Model model) {
        model.addAttribute("customer", this.customerDetailsService.findCustomerByUsername(customerLoginDetailsService.loadAuthenticatedUsername()));
        model.addAttribute("customersAccount", this.accountService.getAccountDetails(customerDetailsService.findCustomerByUsername(customerLoginDetailsService.loadAuthenticatedUsername())));
        model.addAttribute("customersCards", this.cardService.getCardDetails(customerDetailsService.findCustomerByUsername(customerLoginDetailsService.loadAuthenticatedUsername())));
        return "settings";
    }
}
