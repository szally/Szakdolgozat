package com.assignment.persistance.dto;

import com.assignment.domain.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class CustomerDTO {
    private Long id;
    private Credentials credentials;
    private String name;
    private String birthPlace;
    private Date birthDate;
    private String mothersName;
    private String idCardNumb;
    private Long taxNumb;
    private String email;
    private CustomerStatus status;
    private List<Long> accounts = new ArrayList<>();
    private List<Long> cards = new ArrayList<>();
    private List<Long> transactionList = new ArrayList<>();
}
