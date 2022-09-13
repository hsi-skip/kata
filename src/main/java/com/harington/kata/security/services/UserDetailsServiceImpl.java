package com.harington.kata.security.services;

import com.harington.kata.entity.Customer;
import com.harington.kata.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    CustomerRepository customerRepository;

    public UserDetailsServiceImpl(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customrt = customerRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Customer Not Found with username: " + username));
        return UserDetailsImpl.build(customrt);
    }
}
