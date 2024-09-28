package com.assignment.service;

import com.assignment.domain.*;
import com.assignment.repository.CardRepositroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    private CardRepositroy cardRepositroy;


    @Override
    public List<Card> getCardDetails(Customer customer) {
        return customer.getAccounts()
                .stream()
                .map(Account::getCard)
                .collect(Collectors.toList());
    /*    List<Card> allCards = getAllCards();
        List<Card> cardList = customer.getCards();
        Card accountCard = account.getCard();

        for (Card card : allCards) {

            for (Card cardInCustomer : customer.getAccounts().get(0).getCard()) {

                if (card.getId().equals(cardInCustomer.getId())) {
                    cardList.add(card);
                    break;

                }
            }
        }


     *//*   List<Card> cardList = getAllCards().stream()
                .filter(
                        card -> customer.getCards().stream().anyMatch(
                                cardCustomer -> card.getId().equals(cardCustomer.getId())
                        )
                ).collect(Collectors.toList());*//*

        if (cardList.isEmpty()) {
            return Collections.emptyList();
        }
        customer.setCards(cardList);

        return cardList;*/
    }

    @Override
    public void blockCard(Card card) {
        card.setStatus(AccountAndCardStatus.BLOCKED);
    }

    @Override
    public void terminateCard(Card card) {
        card.setStatus(AccountAndCardStatus.TERMINATED);
    }

    @Override
    public Card requestNewCard(Customer customer, String customerName, String type, int pin) {
        List<Card> cardList = cardRepositroy.findAll();
        LocalDate expiryDate = LocalDate.now().plusYears(4);
        Card card = new Card();
        card.setId((long) (cardList.size() + 1));
        card.setCustomer(customer);
        card.setPinCode(pin);
        card.setCustomerName(customer.getName());
        card.setType(CardType.valueOf(type));
        card.setExpiryDate(Date.from(expiryDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        card.setStatus(AccountAndCardStatus.ACTIVE);
        //itt valahogy meg kellene oldani hogy elindul a kártya kibocsáltási folyamat vagy mit tudjam én
        cardList.add(card);

        return card;
    }

    @Override
    public Card updateCardDetails(Customer customer, int pinCode, Card card) {
         card.setPinCode(pinCode);
         return card;
    }
}
