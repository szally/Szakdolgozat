package com.assignment.service;

import com.assignment.domain.Account;
import com.assignment.domain.Card;
import com.assignment.domain.CardType;
import com.assignment.domain.Customer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CardService {
    List<Card> getCardDetails(Customer customer);
    void blockCard(Card card);
     void unBlockCard(Card card);
    void terminateCard(Card card);
    Card requestNewCard(Customer customer, String customerName, String type, int pin);
    Card updateCardDetails(Customer customer, int pinCode, Card card);
    //void reportLostOrStolenCard();
}
