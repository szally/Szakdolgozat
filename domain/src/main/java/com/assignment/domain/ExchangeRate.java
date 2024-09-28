package com.assignment.domain;

import lombok.*;

import java.util.Map;


@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@Getter
@Setter
public class ExchangeRate {
    private Map<String, Double> rates;

    public ExchangeRate(Map<String, Double> rates) {
        this.rates = rates;
    }

}
