package com.assignment.service;

import com.assignment.domain.Credentials;
import com.assignment.domain.Customer;
import com.assignment.repository.CustomerRepository;
import com.assignment.service.exceptions.LoginFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.assignment.domain.CustomerStatus.ACTIVE;
import static com.assignment.domain.CustomerStatus.NEW;

@Service()
public class AuthencticationServiceImpl implements AuthenticationService{

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer login(Credentials credentials) throws LoginFailedException {
        Customer customer = customerRepository.findByCredentialsUsername(credentials.getUsername());

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
