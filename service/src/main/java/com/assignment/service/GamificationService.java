package com.assignment.service;

import com.assignment.domain.Customer;

public interface GamificationService {
    void updatePointsForSavings(Customer customer, double amountSaved, String curreny);
    int calculatePoints(double amountSaved, String currency);
}
