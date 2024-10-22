package com.assignment.service;

import com.assignment.domain.Customer;
import com.assignment.repository.CustomerRepository;
import com.assignment.service.exceptions.InvalidPasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerDetailsServiceImpl implements CustomerDetailsService{

    @Autowired
    CustomerRepository customerRepository;
    @Override
    public Customer updateCustomerDetails(Customer customer,String name, String idCardNumb, String email) {
        customer.setName(name);
        customer.setIdCardNumb(idCardNumb);
        customer.setEmail(email);
        customerRepository.save(customer);
        return customer;
    }

    @Override
    public Customer changePassword(Customer customer, String newPassword) throws InvalidPasswordException {
        if (!isValidPassword(newPassword)) {
            throw new InvalidPasswordException("Invalid password format. Please ensure it meets the requirements.");
        }

        customer.getCredentials().setPassword(newPassword);

        customerRepository.save(customer);

        return customer;
    }

    private boolean isValidPassword(String password) {
        return password.matches("(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)[A-Za-z\\d]{8,}$");
    }

    public Customer findCustomerByUsername(String username) {
        List<Customer> customers = customerRepository.findAll();

        return customers.stream()
                .filter(customer -> customer.getCredentials().getUsername().equals(username))
                .findFirst().orElse(null);
    }


}
