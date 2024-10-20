package com.assignment.controller;

import com.assignment.domain.Account;
import com.assignment.domain.Customer;
import com.assignment.domain.PartnerBank;
import com.assignment.model.AccountModel;
import com.assignment.model.PartnerBankModel;
import com.assignment.model.TransactionListModel;
import com.assignment.model.TransactionsModel;
import com.assignment.security.CustomerLoginDetailsService;
import com.assignment.service.AccountServiceImpl;
import com.assignment.service.CustomerDetailsServiceImpl;
import com.assignment.service.TransactionHistoryServiceImpl;
import com.assignment.service.TransferServiceImpl;
import com.assignment.service.exceptions.InsufficientFundsException;
import com.assignment.service.exceptions.InvalidIbanException;
import com.assignment.service.exceptions.PartnerBankNotFoundException;
import com.assignment.transformer.TransactionTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class TransferController {


    @Autowired
    CustomerDetailsServiceImpl customerDetailsService;

    @Autowired
    CustomerLoginDetailsService customerLoginDetailsService;

    @Autowired
    TransferServiceImpl transferService;

    @Autowired
    AccountServiceImpl accountService;

    @ModelAttribute("accountModel")
    public AccountModel getAccountModel(){
        return new AccountModel();
    }
    @ModelAttribute("transactionModel")
    public TransactionsModel getTransactionModel(){
        return new TransactionsModel();
    }

    @ModelAttribute("partnerBankModel")
    public PartnerBankModel getPartnerBankModel(){
        return new PartnerBankModel();
    }

    @GetMapping({"/transfer-between-own-accounts"})
    public String showTransferBetweenOwnAccount(Model model) {
        model.addAttribute("customersAccount", this.accountService.getAccountDetails(customerDetailsService.findCustomerByUsername(customerLoginDetailsService.loadAuthenticatedUsername())));
        return "transfer-between-own-accounts";
    }
    @PostMapping({"/transfer-between-own-accounts"})
    public String transferBetweenOwnAccounts
            (@ModelAttribute("transactionModel") TransactionsModel transactionModel,Model model, RedirectAttributes redirectAttributes, Long sourceAccountNumber, Long destinationAccountNumber, double amount,String currency,String description)
            throws InsufficientFundsException {
        model.addAttribute("customersAccount", this.accountService.getAccountDetails(customerDetailsService.findCustomerByUsername(customerLoginDetailsService.loadAuthenticatedUsername())));
        sourceAccountNumber = transactionModel.getAccount().getId();
        Account sourceAccount = accountService.findAccountById(sourceAccountNumber);
        destinationAccountNumber = transactionModel.getPartnerAccountNumb();
        Account destinationAccount = accountService.findAccountById(destinationAccountNumber);
        this.transferService.transferBetweenOwnAccounts(sourceAccountNumber, destinationAccountNumber,amount, currency,  description, customerDetailsService.findCustomerByUsername(customerLoginDetailsService.loadAuthenticatedUsername()));
        redirectAttributes.addFlashAttribute("successMessage", "Transfer successful!");

        return "redirect:transfer-between-own-accounts";
    }

    @GetMapping({"/domestic-transfer"})
    public String showDomesticTransfer(Model model) {
        model.addAttribute("customersAccount", this.accountService.getAccountDetails(customerDetailsService.findCustomerByUsername(customerLoginDetailsService.loadAuthenticatedUsername())));
        return "domestic-transfer";
    }

    @PostMapping({"/domestic-transfer"})
    public String domesticTransfer
            (@ModelAttribute("transactionModel") TransactionsModel transactionModel,Model model, RedirectAttributes redirectAttributes,Long sourceAccountNumber, Long destinationAccountNumber,double amount,String currency, String description, String partner)
            throws InsufficientFundsException {
        model.addAttribute("customersAccount", this.accountService.getAccountDetails(customerDetailsService.findCustomerByUsername(customerLoginDetailsService.loadAuthenticatedUsername())));
        sourceAccountNumber = transactionModel.getAccount().getId();
        Account sourceAccount = accountService.findAccountById(sourceAccountNumber);
        destinationAccountNumber = transactionModel.getPartnerAccountNumb();
        partner = transactionModel.getPartnerName();
        this.transferService.domesticTransfer(sourceAccountNumber, destinationAccountNumber,amount, currency,  description,partner, customerDetailsService.findCustomerByUsername(customerLoginDetailsService.loadAuthenticatedUsername()));
        redirectAttributes.addFlashAttribute("successMessage", "Transfer successful!");

        return "redirect:domestic-transfer";
    }

    @GetMapping({"/international-transfer"})
    public String showInternationalTransfer(Model model, @ModelAttribute("partnerBankModel") PartnerBankModel partnerBankModel) {
        model.addAttribute("customersAccount", this.accountService.getAccountDetails(customerDetailsService.findCustomerByUsername(customerLoginDetailsService.loadAuthenticatedUsername())));
        return "international-transfer";
    }

  /*  @GetMapping({"/international-transfer"})
    public String showPartnerBank(Model model, @ModelAttribute("partnerBankModel") PartnerBankModel partnerBankModel, @RequestParam("swiftCode") String swift) {
        //model.addAttribute("customersAccount", this.accountService.getAccountDetails(customerDetailsService.findCustomerByUsername(customerLoginDetailsService.loadAuthenticatedUsername())));
        model.addAttribute("partnerBanks", this.transferService.findPartnerBankBySwift(swift));
        return "international-transfer";
    }*/

    @PostMapping({"/international-transfer"})
    public String internationalTransfer
            (@ModelAttribute("transactionModel") TransactionsModel transactionModel,@ModelAttribute("partnerBankModel") PartnerBankModel partnerBankModel,Model model,RedirectAttributes redirectAttributes,Long sourceAccountNumber,Long destinationAccountNumber,double amount,String currency,String description, String partner,String targetIban,String swift)
            throws InsufficientFundsException, PartnerBankNotFoundException, InvalidIbanException {
        model.addAttribute("customersAccount", this.accountService.getAccountDetails(customerDetailsService.findCustomerByUsername(customerLoginDetailsService.loadAuthenticatedUsername())));
        sourceAccountNumber = transactionModel.getAccount().getId();
        Account sourceAccount = accountService.findAccountById(sourceAccountNumber);
        destinationAccountNumber = transactionModel.getPartnerAccountNumb();
        partner = transactionModel.getPartnerName();
        targetIban = transactionModel.getAccount().getIban().getIban();
        swift = partnerBankModel.getSwiftCode();
       // showPartnerBank(model, partnerBankModel, swift);
        this.transferService.internationalTransfer(sourceAccountNumber, destinationAccountNumber,amount, currency,  description, partner ,customerDetailsService.findCustomerByUsername(customerLoginDetailsService.loadAuthenticatedUsername()), targetIban, swift);
        redirectAttributes.addFlashAttribute("successMessage", "Transfer successful!");

        return "redirect:international-transfer";
    }

}
