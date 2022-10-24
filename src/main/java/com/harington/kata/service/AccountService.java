package com.harington.kata.service;

import com.harington.kata.dto.AccountDto;

import java.util.Optional;
import java.util.Set;

public interface AccountService {

    Optional<AccountDto> getAccountById(long id);

    double getBalance(Long accountId);

    void deposit(Long accountId, double amount);

    void withdrawal(Long accountId, double amount);

}
