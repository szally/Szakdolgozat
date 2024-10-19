package com.assignment.controller;

import com.assignment.domain.Account;
import com.assignment.domain.Customer;
import com.assignment.model.AccountListModel;
import com.assignment.model.AccountModel;
import com.assignment.security.CustomerLoginDetailsService;
import com.assignment.service.AccountServiceImpl;
import com.assignment.service.CustomerDetailsServiceImpl;
import com.assignment.transformer.AccountTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class AccountController {
    @Autowired
    AccountServiceImpl accountService;
    @Autowired
    CustomerDetailsServiceImpl  customerDetailsService;

    @Autowired
    AccountTransformer accountTransformer;

    @Autowired
    CustomerLoginDetailsService customerLoginDetailsService;


    public AccountController() {
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

    @GetMapping({"/accounts"})
    public String showAccounts(Model model) {
        model.addAttribute("customersAccount", this.accountService.getAccountDetails(customerDetailsService.findCustomerByUsername(customerLoginDetailsService.loadAuthenticatedUsername())));
        //model.addAttribute("account", this.accountService.findAllAccounts());
        return "accounts";
    }

    @PostMapping({"/open-new-account"})
    public String addNewAccount(String currency, RedirectAttributes redirectAttributes) {
            //Account account = this.accountTransformer.transformAccountModelToAccount(accountModel);
            this.accountService.openNewAccount(customerDetailsService.findCustomerByUsername(customerLoginDetailsService.loadAuthenticatedUsername()), currency);
            redirectAttributes.addFlashAttribute("successMessage", "Game added successfully!");

        return "redirect:accounts";
    }

    @PostMapping({"/block-account"})
    public String blockAccount(@RequestParam("accountId") Long accountId, RedirectAttributes redirectAttributes){
        Account account = accountService.findAccountById(accountId);
        accountService.blockAccount(account);
        redirectAttributes.addFlashAttribute("successMessage", "Account blocked successfully!");
        return "redirect:accounts";
    }

    @PostMapping({"/unblock-account"})
    public String unblockAccount(@RequestParam("accountId") Long accountId, RedirectAttributes redirectAttributes){
        Account account = accountService.findAccountById(accountId);
        accountService.unBlockAccount(account);
        redirectAttributes.addFlashAttribute("successMessage", "Account unblocked successfully!");
        return "redirect:accounts";
    }

    @PostMapping({"/close-account"})
    public String closeAccount(@RequestParam("accountId") Long accountId, RedirectAttributes redirectAttributes){
        Account account = accountService.findAccountById(accountId);
        accountService.closeAccount(account);
        redirectAttributes.addFlashAttribute("successMessage", "Account closed successfully!");
        return "redirect:accounts";
    }
}
