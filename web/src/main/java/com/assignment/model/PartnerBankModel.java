package com.assignment.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@NoArgsConstructor
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
public class PartnerBankModel {
    private Long id;
    private String name;
    private String swiftCode;
    private double transferFeeInEUR;
}
