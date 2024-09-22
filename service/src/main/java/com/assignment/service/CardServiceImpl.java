package com.assignment.service;

import com.assignment.domain.*;
import com.assignment.persistance.FileDataStore;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class CardServiceImpl implements CardService{

    private final FileDataStore dataStore ;

    public CardServiceImpl(FileDataStore dataStore) {
        this.dataStore = dataStore;
    }

    public List<Card> getAllCards() {
        return dataStore.getCards();
    }
    @Override
    public List<Card> getCardDetails(Customer customer) {
        List<Card> allCards = getAllCards();

        List<Card> cardList = getAllCards().stream()
                .filter(
                        card -> customer.getCards().stream().anyMatch(
                                cardCustomer -> card.getId().equals(cardCustomer.getId())
                        )
                ).collect(Collectors.toList());

        if (cardList.isEmpty()) {
            return Collections.emptyList();
        }
        customer.setCards(cardList);

        return cardList;
    }

    @Override
    public void blockCard(Card card) {
       card.setStatus(AccountAndCardStatus.BLOCKED);
    }

    @Override
    public void terminatekCard(Card card) {
        card.setStatus(AccountAndCardStatus.TERMINATED);
    }

    @Override
    public Card requestNewCard(Customer customer, String customerName, CardType type) {
        List<Card> cardList = getAllCards();
        LocalDate expiryDate = LocalDate.now().plusYears(4);
        Card card = new Card();
        card.setCustomer(customer);
        card.setCustomerName(customer.getName());
        card.setType(type);
        card.setExpiryDate(Date.from(expiryDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        card.setStatus(AccountAndCardStatus.ACTIVE);
        //itt valahogy meg kellene oldani hogy elindul a kártya kibocsáltási folyamat vagy mit tudjam én
        cardList.add(card);

        return card;
    }

    @Override
    public void  updateCardDetails(Customer customer, int pinCode) {
        List<Card> customersCard = getCardDetails(customer);
        customersCard.stream().forEach(card -> card.setPinCode(pinCode));
    }
}
