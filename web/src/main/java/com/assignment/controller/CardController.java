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
    @ModelAttribute("accountModel")
    public CardModel getCardModel(){
        return new CardModel();
    }

    @ModelAttribute("accountListModel")
    public CardListModel getCardListModel() {
        return new CardListModel(this.cardTransformer
                .transformCardListToCardModelList(this.cardService.getCardDetails(customerDetailsService.findCustomerByUsername(customerLoginDetailsService.loadAuthenticatedUsername()))));
    }

    @GetMapping({"/cards"})
    public String showCards(Model model) {
        model.addAttribute("customersAccount", getCardListModel());
        model.addAttribute("account", this.cardService.findAllCards());
        return "player-games-page";
    }

    @PostMapping({"/request-new-card"})
    public String requestNewCard(String currency, RedirectAttributes redirectAttributes, @RequestParam("customerName") String customerName, @RequestParam("cardType") String cardType, @RequestParam("pinCode") int pinCode) {
        //Account account = this.accountTransformer.transformAccountModelToAccount(accountModel);
        this.cardService.requestNewCard(customerDetailsService.findCustomerByUsername(customerLoginDetailsService.loadAuthenticatedUsername()), customerName, cardType, pinCode);
        redirectAttributes.addFlashAttribute("successMessage", "New card requested successfully!");

        return "redirect:manage-games";
    }

    @PostMapping({"/update-card-details"})
    public String updateCardDetails(@RequestParam("cardId") Long cardId, @RequestParam("pin") int pin, RedirectAttributes redirectAttributes){
        Card card = cardService.findCardById(cardId);
        cardService.updateCardDetails(customerDetailsService.findCustomerByUsername(customerLoginDetailsService.loadAuthenticatedUsername()), pin, card);
        redirectAttributes.addFlashAttribute("successMessage", "Account blocked successfully!");
        return "redirect:manage-games";
    }

    @PostMapping({"/block-card"})
    public String blockCard(@RequestParam("cardId") Long cardId, RedirectAttributes redirectAttributes){
        Card card = cardService.findCardById(cardId);
        cardService.blockCard(card);
        redirectAttributes.addFlashAttribute("successMessage", "Account unblocked successfully!");
        return "redirect:manage-games";
    }

    @PostMapping({"/unblock-card"})
    public String unBlockCard(@RequestParam("cardId") Long cardId, RedirectAttributes redirectAttributes){
        Card card = cardService.findCardById(cardId);
        cardService.unBlockCard(card);
        redirectAttributes.addFlashAttribute("successMessage", "Account unblocked successfully!");
        return "redirect:manage-games";
    }

    @PostMapping({"/terminate-card"})
    public String terminateCard(@RequestParam("cardId") Long cardId, RedirectAttributes redirectAttributes){
        Card card = cardService.findCardById(cardId);
        cardService.terminateCard(card);
        redirectAttributes.addFlashAttribute("successMessage", "Account closed successfully!");
        return "redirect:manage-games";
    }
}
