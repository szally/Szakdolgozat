package com.assignment.service;

import com.assignment.domain.Credentials;
import com.assignment.domain.Customer;
import com.assignment.domain.CustomerStatus;
import com.assignment.persistance.FileDataStore;
import com.assignment.service.exceptions.LoginFailedException;

import java.util.ArrayList;
import java.util.List;

import static com.assignment.domain.CustomerStatus.ACTIVE;
import static com.assignment.domain.CustomerStatus.NEW;

public class AuthencticationServiceImpl implements AuthenticationService{

    private final FileDataStore dataStore ;

    public AuthencticationServiceImpl(FileDataStore fileDataStore) {
        this.dataStore = fileDataStore;

    }
    @Override
    public Customer login(Credentials credentials) throws LoginFailedException {
        Customer customer = dataStore.getCustomerByCredentials(credentials.getUsername());

        if (customer != null && customer.getCredentials().getPassword().equals(credentials.getPassword())) {
            System.out.println("Login success yaay!!");
            setNewCustomerToActive(customer);
            return customer;
        } else {
            throw new LoginFailedException("Login failure, bye.\n");
        }
    }

    public void setNewCustomerToActive(Customer customer){
        if(customer.getStatus().equals(NEW)){
            customer.setStatus(ACTIVE);
        }
    }
}
