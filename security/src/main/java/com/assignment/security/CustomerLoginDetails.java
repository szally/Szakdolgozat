package com.assignment.security;

import com.assignment.domain.Customer;
import com.assignment.domain.CustomerStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;

public class CustomerLoginDetails implements UserDetails {

    private Customer customer;

    public CustomerLoginDetails(Customer customer) {
        this.customer = customer;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        CustomerStatus customerStatus = this.customer.getStatus();
        Collection<GrantedAuthority> authorities = new HashSet<GrantedAuthority>(customerStatus.ordinal());
        authorities.add(new SimpleGrantedAuthority("STATUS_" + customerStatus));
        return authorities;
    }

    @Override
    public String getPassword() {
       return this.customer.getCredentials().getPassword();
    }

    @Override
    public String getUsername() {
        return this.customer.getCredentials().getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
