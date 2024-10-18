package com.assignment.controller;

import com.assignment.repository.CustomerRepository;
import com.assignment.security.CustomerLoginDetailsService;
import com.assignment.service.CustomerDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerLoginDetailsService customerLoginDetailsService;

    @Autowired
    CustomerDetailsServiceImpl customerDetailsService;

    public HomeController() {
    }

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("customer", this.customerDetailsService.findCustomerByUsername(customerLoginDetailsService.loadAuthenticatedUsername()));
        return "home-page";
    }
}
