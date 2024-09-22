package com.assignment.service;

import com.assignment.domain.Credentials;
import com.assignment.domain.Customer;
import com.assignment.service.exceptions.LoginFailedException;

public interface AuthenticationService {
    Customer login(Credentials credentials) throws LoginFailedException;
}
