package com.harington.kata.service;

import com.harington.kata.dto.CustomerDto;

public interface CustomerService {

    CustomerDto getCustomerById(Long id);

}
