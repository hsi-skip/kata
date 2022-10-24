package com.harington.kata.service.impl;

import com.harington.kata.dto.CustomerDto;
import com.harington.kata.repository.CustomerRepository;
import com.harington.kata.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper modelMapper){
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CustomerDto getCustomerById(Long customerId){
        return customerRepository.findById(customerId)
                .map(customer -> modelMapper.map(customer, CustomerDto.class))
                .orElseThrow(() -> new IllegalArgumentException("Customer id: " + customerId + " Not found"));
    }

}
