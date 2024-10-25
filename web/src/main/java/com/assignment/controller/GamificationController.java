package com.assignment.controller;

import com.assignment.security.CustomerLoginDetailsService;
import com.assignment.service.AccountServiceImpl;
import com.assignment.service.CardServiceImpl;
import com.assignment.service.CustomerDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GamificationController {
    @Autowired
    private CustomerLoginDetailsService customerLoginDetailsService;
    @Autowired
    CustomerDetailsServiceImpl customerDetailsService;
    @Autowired
    AccountServiceImpl accountService;

    @GetMapping("/gamification")
    public String gamification(Model model) {
        model.addAttribute("customer", this.customerDetailsService.findCustomerByUsername(customerLoginDetailsService.loadAuthenticatedUsername()));
        model.addAttribute("customersSavingAccount", this.accountService.getSavingAccount(customerDetailsService.findCustomerByUsername(customerLoginDetailsService.loadAuthenticatedUsername())));
        return "gamification";
    }
}
