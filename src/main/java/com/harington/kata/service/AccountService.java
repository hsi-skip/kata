package com.harington.kata.service;

import com.harington.kata.dto.AccountDto;

public interface AccountService {

    AccountDto getAccountById(long id);

    AccountDto save(AccountDto accountDto);

    AccountDto deposit(AccountDto accountDto, double amount);

    AccountDto withdrawal(AccountDto accountDto, double amount);

    double getBalance(AccountDto accountDto);
}
