package com.harington.kata.service.impl;

import com.harington.kata.dto.AccountDto;
import com.harington.kata.entity.Account;
import com.harington.kata.repository.AccountRepository;
import com.harington.kata.service.AccountService;
import com.harington.kata.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final CustomerService customerService;
    private final ModelMapper modelMapper;

    Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    public AccountServiceImpl(AccountRepository accountRepository, CustomerService customerService,ModelMapper modelMapper){
        this.accountRepository = accountRepository;
        this.customerService = customerService;
        this.modelMapper = modelMapper;
    }

    @Override
    public Optional<AccountDto> getAccountById(long id){
        return accountRepository.findById(id).map(account -> modelMapper.map(account, AccountDto.class));
    }

    @Override
    public double getBalance(Long accountId){
        return accountRepository.findById(accountId).map(Account::getAmount)
                .orElseThrow(() -> new IllegalArgumentException("Account id: " + accountId + " not found"));
    }

    @Override
    public void deposit(Long accountId, double amount){
        if(amount <= 0){
            logger.info("Amount {} is negative or Zero", amount);
            throw new IllegalArgumentException("Amount " + amount + " is negative or Zero");
        }

        accountRepository.findById(accountId).ifPresent(
                account -> {
                    account.setAmount(account.getAmount() + amount);
                    accountRepository.save(account);
                }
        );
    }

    @Override
    public void withdrawal(Long accountId, double amount){

        if(getBalance(accountId) < amount){
            logger.warn("Amount is greater than Balance");
            throw new IllegalArgumentException("Amount is greater than Balance");
        }

        accountRepository.findById(accountId).ifPresent(
                account ->
                {
                    account.setAmount(account.getAmount() - amount);
                    accountRepository.save(account);
                }
        );
    }

}
