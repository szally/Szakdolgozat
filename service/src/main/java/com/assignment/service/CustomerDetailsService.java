package com.assignment.service;

import com.assignment.domain.Account;
import com.assignment.domain.Customer;
import com.assignment.service.exceptions.InvalidPasswordException;

import java.util.List;

public interface CustomerDetailsService {
    Customer updateCustomerDetails(Customer customer,String name, String idCardNumb, String email);
    Customer changePassword(Customer customer, String newPassword) throws InvalidPasswordException;
}
