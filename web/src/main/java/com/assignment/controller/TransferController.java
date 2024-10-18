package com.assignment.controller;

import com.assignment.domain.Customer;
import com.assignment.security.CustomerLoginDetailsService;
import com.assignment.service.CustomerDetailsServiceImpl;
import com.assignment.service.TransferServiceImpl;
import com.assignment.service.exceptions.InsufficientFundsException;
import com.assignment.service.exceptions.InvalidIbanException;
import com.assignment.service.exceptions.PartnerBankNotFoundException;
import com.assignment.transformer.TransactionTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class TransferController {

    @Autowired
    TransactionTransformer transactionTransformer;

    @Autowired
    CustomerDetailsServiceImpl customerDetailsService;

    @Autowired
    CustomerLoginDetailsService customerLoginDetailsService;

    @Autowired
    TransferServiceImpl transferService;
    @PostMapping({"/transfer-between-own-accounts"})
    public String transferBetweenOwnAccounts
            (RedirectAttributes redirectAttributes, @RequestParam("sourceAccountNumber")Long sourceAccountNumber, @RequestParam("destinationAccountNumber")Long destinationAccountNumber, @RequestParam("amount")double amount,  @RequestParam("currency")String currency, @RequestParam("description")String description)
            throws InsufficientFundsException {
        this.transferService.transferBetweenOwnAccounts(sourceAccountNumber, destinationAccountNumber,amount, currency,  description, customerDetailsService.findCustomerByUsername(customerLoginDetailsService.loadAuthenticatedUsername()));
        redirectAttributes.addFlashAttribute("successMessage", "Transfer successful!");

        return "redirect:transfer";
    }

    @PostMapping({"/domestic-transfer"})
    public String domesticTransfer
            (RedirectAttributes redirectAttributes, @RequestParam("sourceAccountNumber")Long sourceAccountNumber, @RequestParam("destinationAccountNumber")Long destinationAccountNumber, @RequestParam("amount")double amount,  @RequestParam("currency")String currency, @RequestParam("description")String description, @RequestParam("partner")String partner)
            throws InsufficientFundsException {
        this.transferService.domesticTransfer(sourceAccountNumber, destinationAccountNumber,amount, currency,  description,partner, customerDetailsService.findCustomerByUsername(customerLoginDetailsService.loadAuthenticatedUsername()));
        redirectAttributes.addFlashAttribute("successMessage", "Transfer successful!");

        return "redirect:transfer";
    }

    @PostMapping({"/international-transfer"})
    public String internationalTransfer
            (RedirectAttributes redirectAttributes, @RequestParam("sourceAccountNumber")Long sourceAccountNumber, @RequestParam("destinationAccountNumber")Long destinationAccountNumber, @RequestParam("amount")double amount,  @RequestParam("currency")String currency, @RequestParam("description")String description, @RequestParam("partner")String partner,  @RequestParam("targetIban") String targetIban,  @RequestParam("swift")String swift)
            throws InsufficientFundsException, PartnerBankNotFoundException, InvalidIbanException {
        this.transferService.internationalTransfer(sourceAccountNumber, destinationAccountNumber,amount, currency,  description, partner ,customerDetailsService.findCustomerByUsername(customerLoginDetailsService.loadAuthenticatedUsername()), targetIban, swift);
        redirectAttributes.addFlashAttribute("successMessage", "Transfer successful!");

        return "redirect:transfer";
    }

}
