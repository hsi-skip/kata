package com.harington.kata.service.impl;

import com.harington.kata.dto.CustomerDto;
import com.harington.kata.entity.Customer;
import com.harington.kata.repository.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)

class CustomerServiceImplTest {

    @InjectMocks
    private CustomerServiceImpl customerServiceImpl ;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ModelMapper modelMapper;

    @Test
    void testGetCustomerById(){
        Customer customer = new Customer() ;
        customer.setId(1L);
        Mockito.when(this.customerRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(customer));
        BDDMockito.given(this.modelMapper.map(customer, CustomerDto.class)).willReturn(new CustomerDto());
        Assertions.assertNotNull(this.customerServiceImpl.getCustomerById(1L));
    }

}
