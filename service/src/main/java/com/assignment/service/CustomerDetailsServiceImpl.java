package com.assignment.service;

import com.assignment.domain.Account;
import com.assignment.domain.Customer;
import com.assignment.persistance.FileDataStore;
import com.assignment.service.exceptions.InvalidPasswordException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerDetailsServiceImpl implements CustomerDetailsService{

    private final FileDataStore dataStore ;

    public CustomerDetailsServiceImpl(FileDataStore dataStore) {
        this.dataStore = dataStore;
    }

    @Override
    public Customer updateCustomerDetails(Customer customer,String name, String idCardNumb, String email) {
        customer.setName(name);
        customer.setIdCardNumb(idCardNumb);
        customer.setEmail(email);
        return customer;
    }

    @Override
    public Customer changePassword(Customer customer, String newPassword) throws InvalidPasswordException {
        if (!isValidPassword(newPassword)) {
            throw new InvalidPasswordException("Invalid password format. Please ensure it meets the requirements.");
        }
        customer.getCredentials().setPassword(newPassword);

        return customer;
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 8
                && password.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)");
    }
}
