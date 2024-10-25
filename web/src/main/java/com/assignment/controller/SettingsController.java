package com.assignment.controller;

import com.assignment.domain.Card;
import com.assignment.model.CardModel;
import com.assignment.repository.CustomerRepository;
import com.assignment.security.CustomerLoginDetailsService;
import com.assignment.service.AccountServiceImpl;
import com.assignment.service.CardServiceImpl;
import com.assignment.service.CustomerDetailsServiceImpl;
import com.assignment.service.exceptions.InvalidPasswordException;
import com.assignment.transformer.AccountTransformer;
import com.assignment.transformer.CardTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SettingsController {

    @Autowired
    private CustomerLoginDetailsService customerLoginDetailsService;
    @Autowired
    CustomerDetailsServiceImpl customerDetailsService;
    @Autowired
    AccountServiceImpl accountService;
    @Autowired
    CardServiceImpl cardService;

    @GetMapping("/settings")
    public String settings(Model model) {
        model.addAttribute("customer", this.customerDetailsService.findCustomerByUsername(customerLoginDetailsService.loadAuthenticatedUsername()));
        model.addAttribute("customersAccount", this.accountService.getAccountDetails(customerDetailsService.findCustomerByUsername(customerLoginDetailsService.loadAuthenticatedUsername())));
        model.addAttribute("customersCards", this.cardService.getCardDetails(customerDetailsService.findCustomerByUsername(customerLoginDetailsService.loadAuthenticatedUsername())));
        return "settings";
    }

    @PostMapping({"/update-customer-details"})
    public String updateCustomerDetails(String name, String idCardNumb, String email, RedirectAttributes redirectAttributes){
            customerDetailsService.updateCustomerDetails(customerDetailsService.findCustomerByUsername(customerLoginDetailsService.loadAuthenticatedUsername()), name, idCardNumb,email);
            redirectAttributes.addFlashAttribute("successMessage", "Account blocked successfully!");
        return "redirect:settings";

    }

    @PostMapping({"/update-password"})
    public String updatePassword(String newPassword, RedirectAttributes redirectAttributes) throws InvalidPasswordException {
        customerDetailsService.changePassword(customerDetailsService.findCustomerByUsername(customerLoginDetailsService.loadAuthenticatedUsername()), newPassword);
        redirectAttributes.addFlashAttribute("successMessage", "Account blocked successfully!");
        return "redirect:settings";

    }

    @PostMapping({"/update-card-details"})
    public String updateCardDetails(@RequestParam("cardId") Long cardId, @ModelAttribute("cardModel") CardModel cardModel, int pin, RedirectAttributes redirectAttributes, Model model){
        model.addAttribute("customersCards", this.cardService.getCardDetails(customerDetailsService.findCustomerByUsername(customerLoginDetailsService.loadAuthenticatedUsername())));
        model.addAttribute("cardModel", cardModel);
        Card card = cardService.findCardById(cardId);
        cardService.updateCardDetails(customerDetailsService.findCustomerByUsername(customerLoginDetailsService.loadAuthenticatedUsername()), pin, card);
        redirectAttributes.addFlashAttribute("successMessage", "Account blocked successfully!");
        return "redirect:settings";

    }
}
