package com.assignment.security;

import com.assignment.domain.Customer;
import com.assignment.service.CustomerDetailsServiceImpl;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class CustomerLoginDetailsService implements UserDetailsService {

    @Autowired
    CustomerDetailsServiceImpl customerDetailsService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = this.customerDetailsService.findCustomerByUsername(username);

        if(customer.equals(null)){
            throw new UsernameNotFoundException("Wrong Username or Password");
        }else {
            return new CustomerLoginDetails(customer);
        }
    }

    public String loadAuthenticatedUsername(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if(principal instanceof UserDetails){
            username = ((UserDetails) principal).getUsername();
        }else {
            username = principal.toString();
        }

        return username;
    }
}
