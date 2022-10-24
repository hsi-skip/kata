package com.harington.kata.service.impl;

import com.harington.kata.entity.Customer;
import com.harington.kata.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CustomerServiceImplTest {

    CustomerServiceImpl customerService;
    @Mock CustomerRepository customerRepository;

    @BeforeEach
    void setUp(){
        ModelMapper mapper = new ModelMapper();
        customerService = new CustomerServiceImpl(customerRepository, mapper);
    }

    @Test
    void testGetCustomerById(){
        Long customerId = 1L;
        Customer customer = new Customer();
        customer.setId(customerId);

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        customerService.getCustomerById(customerId);
        verify(customerRepository, times(1)).findById(customerId);
    }




    @Test
    void testGetCustomerByIdWhenReturnEmptyOptional(){
        Long customerId = 1L;
        given(customerRepository.findById(customerId)).willReturn(Optional.empty());

        assertThatThrownBy(() -> customerService.getCustomerById(customerId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Customer id: " + customerId + " Not found");

        verify(customerRepository, times(1)).findById(customerId);
    }

}

