package com.assignment.repository;

import com.assignment.domain.Customer;
import com.assignment.domain.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {
    ExchangeRate findByBaseCurrencyAndTargetCurrency(String sourceCurrency, String targetCurrency);
}
