package com.assignment.controller;


import com.assignment.domain.Card;
import com.assignment.domain.Customer;
import com.assignment.model.CardListModel;
import com.assignment.model.CardModel;
import com.assignment.security.CustomerLoginDetailsService;
import com.assignment.service.CardServiceImpl;
import com.assignment.service.CustomerDetailsServiceImpl;
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
public class CardController {
    @Autowired
    CustomerDetailsServiceImpl customerDetailsService;

    @Autowired
    CustomerLoginDetailsService customerLoginDetailsService;

    @Autowired
    CardServiceImpl cardService;

    @Autowired
    CardTransformer cardTransformer;
    @ModelAttribute("cardModel")
    public CardModel getCardModel(){
        return new CardModel();
    }

    @ModelAttribute("cardListModel")
    public CardListModel getCardListModel() {
        return new CardListModel(this.cardTransformer
                .transformCardListToCardModelList(this.cardService.getCardDetails(customerDetailsService.findCustomerByUsername(customerLoginDetailsService.loadAuthenticatedUsername()))));
    }

    @GetMapping({"/cards"})
    public String showCards(Model model) {
        model.addAttribute("customersCards", cardService.getCardDetails(customerDetailsService.findCustomerByUsername(customerLoginDetailsService.loadAuthenticatedUsername())));
        model.addAttribute("customer", this.customerDetailsService.findCustomerByUsername(customerLoginDetailsService.loadAuthenticatedUsername()));

        return "cards";
    }

    @PostMapping({"/request-new-card"})
    public String requestNewCard(RedirectAttributes redirectAttributes, String type,int pinCode) {
        this.cardService.requestNewCard(customerDetailsService.findCustomerByUsername(customerLoginDetailsService.loadAuthenticatedUsername()), type, pinCode);
        redirectAttributes.addFlashAttribute("successMessage", "New card requested successfully!");

        return "redirect:cards";
    }

    @PostMapping({"/update-card-details"})
    public String updateCardDetails(@RequestParam("cardId") Long cardId, int pin, RedirectAttributes redirectAttributes, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return "error";
        } else {
            Card card = cardService.findCardById(cardId);
            cardService.updateCardDetails(customerDetailsService.findCustomerByUsername(customerLoginDetailsService.loadAuthenticatedUsername()), pin, card);
            redirectAttributes.addFlashAttribute("successMessage", "Account blocked successfully!");
        }
        return "redirect:cards";

    }

    @PostMapping({"/block-card"})
    public String blockCard(@RequestParam("cardId") Long cardId, RedirectAttributes redirectAttributes){
        Card card = cardService.findCardById(cardId);
        cardService.blockCard(card);
        redirectAttributes.addFlashAttribute("successMessage", "Account unblocked successfully!");
        return "redirect:cards";
    }

    @PostMapping({"/unblock-card"})
    public String unBlockCard(@RequestParam("cardId") Long cardId, RedirectAttributes redirectAttributes){
        Card card = cardService.findCardById(cardId);
        cardService.unBlockCard(card);
        redirectAttributes.addFlashAttribute("successMessage", "Account unblocked successfully!");
        return "redirect:cards";
    }

    @PostMapping({"/terminate-card"})
    public String terminateCard(@RequestParam("cardId") Long cardId, RedirectAttributes redirectAttributes){
        Card card = cardService.findCardById(cardId);
        cardService.terminateCard(card);
        redirectAttributes.addFlashAttribute("successMessage", "Account closed successfully!");
        return "redirect:cards";
    }
}
