package com.harington.kata.service.impl;

import com.harington.kata.dto.CustomerDto;
import com.harington.kata.entity.Customer;
import com.harington.kata.repository.CustomerRepository;
import com.harington.kata.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper modelMapper){
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CustomerDto getCustomerById(long id){
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        return optionalCustomer.map(customer -> modelMapper.map(customer, CustomerDto.class)).orElse(null);
    }

}
