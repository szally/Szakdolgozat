package com.assignment.service;

import com.assignment.domain.*;
import com.assignment.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    private CardRepository cardRepository;


    @Override
    public List<Card> getCardDetails(Customer customer) {
        return cardRepository.findCardByCustomer(customer);
    }

    @Override
    public void blockCard(Card card) {
        card.setStatus(AccountAndCardStatus.BLOCKED);
        cardRepository.save(card);
    }

    @Override
    public void unBlockCard(Card card) {
        card.setStatus(AccountAndCardStatus.ACTIVE);
        cardRepository.save(card);
    }

    @Override
    public void terminateCard(Card card) {
        card.setStatus(AccountAndCardStatus.TERMINATED);
        cardRepository.save(card);
    }

    @Override
    public Card requestNewCard(Customer customer, String customerName, String type, int pin) {
        List<Card> cardList = cardRepository.findAll();
        LocalDate expiryDate = LocalDate.now().plusYears(4);
        Card card = new Card();
        card.setPinCode(pin);
        card.setCustomerName(customer.getName());
        card.setType(CardType.valueOf(type));
        card.setExpiryDate(Date.from(expiryDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        card.setStatus(AccountAndCardStatus.ACTIVE);
        card.setCustomer(customer);
        //itt valahogy meg kellene oldani hogy elindul a kártya kibocsáltási folyamat vagy mit tudjam én
        cardList.add(card);

        cardRepository.save(card);
        return card;
    }

    @Override
    public Card updateCardDetails(Customer customer, int pinCode, Card card) {
         card.setPinCode(pinCode);
         cardRepository.save(card);
         return card;
    }
}
