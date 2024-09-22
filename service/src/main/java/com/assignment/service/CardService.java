package com.assignment.service;

import com.assignment.domain.Account;
import com.assignment.domain.Card;
import com.assignment.domain.CardType;
import com.assignment.domain.Customer;

import java.util.List;

public interface CardService {
    List<Card> getCardDetails(Customer customer);
    void blockCard(Card card);
    void terminatekCard(Card card);
    Card requestNewCard(Customer customer, String customerName, CardType type);
    void updateCardDetails(Customer customer, int pinCode);
    //void reportLostOrStolenCard();
}
