package com.assignment.service;

import com.assignment.domain.Customer;
import com.assignment.repository.CustomerRepository;
import com.assignment.repository.ExchangeRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GamificationServiceImpl implements GamificationService{

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    TransferServiceImpl transferService;

    @Override
    public void updatePointsForSavings(Customer customer, double amountSaved, String curreny) {
        int points = calculatePoints(amountSaved, curreny);
        customer.setPoints(customer.getPoints() + points);
        customerRepository.save(customer);
    }

    @Override
    public int calculatePoints(double amountSaved, String currency) {
        double amountInBaseCurrency = transferService.convertAmount(currency, "HUF", amountSaved);

        //1000FT-ként 1 pont?
        return (int) (amountInBaseCurrency / 1000);
    }
}
