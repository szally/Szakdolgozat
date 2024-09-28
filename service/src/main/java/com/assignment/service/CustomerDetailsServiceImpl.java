package com.assignment.service;

import com.assignment.domain.Customer;
import com.assignment.service.exceptions.InvalidPasswordException;
import org.springframework.stereotype.Service;

@Service
public class CustomerDetailsServiceImpl implements CustomerDetailsService{

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
