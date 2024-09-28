package com.assignment.persistance.dto;


import lombok.Data;

import java.util.Map;

@Data
public class ExchangeRateDTO {
    private Map<String, Double> rates;
}
