package com.harington.kata.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class AccountDto {
    private Long id;
    private String rib;
    private String swift;
    private String iban;
    private double amount;
    private CustomerDto customer;
}
