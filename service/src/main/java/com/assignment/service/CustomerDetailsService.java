package com.assignment.service;

import com.assignment.domain.Account;
import com.assignment.domain.Customer;
import com.assignment.service.exceptions.InvalidPasswordException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerDetailsService {
    Customer updateCustomerDetails(Customer customer,String name, String idCardNumb, String email);
    Customer changePassword(Customer customer, String newPassword) throws InvalidPasswordException;
}
