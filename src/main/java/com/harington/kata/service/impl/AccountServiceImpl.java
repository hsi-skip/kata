package com.harington.kata.service.impl;

import com.harington.kata.dto.AccountDto;
import com.harington.kata.entity.Account;
import com.harington.kata.repository.AccountRepository;
import com.harington.kata.service.AccountService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;

    Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    public AccountServiceImpl(AccountRepository accountRepository, ModelMapper modelMapper){
        this.accountRepository = accountRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public AccountDto getAccountById(long id){
        Optional<Account> optionalAccount = accountRepository.findById(id);
        return optionalAccount.map(account -> modelMapper.map(account, AccountDto.class)).orElse(null);
    }

    @Override
    public AccountDto save(AccountDto accountDto){
        Account accountSaved = accountRepository.save(modelMapper.map(accountDto, Account.class));
        return modelMapper.map(accountSaved, AccountDto.class);
    }

    @Override
    public AccountDto deposit(AccountDto accountDto, double amount){
        if(amount <= 0){
            logger.info("Amount {} is negative", amount);
            return null;
        }
        AccountDto newAccount = null;
        if(accountDto != null && accountDto.getId() != null){
            Optional<Account> optionalAccount = accountRepository.findById(accountDto.getId());
            if(optionalAccount.isPresent()){
                newAccount = modelMapper.map(optionalAccount.get(), AccountDto.class);
                newAccount.setAmount(newAccount.getAmount() + amount);
                newAccount = save(newAccount);
            }
        }

        return newAccount;
    }

    @Override
    public AccountDto withdrawal(AccountDto accountDto, double amount){

        if(amount <= 0 || getBalance(accountDto) < amount){
            logger.warn("Amount {} is less than 0 or greater than Balance", amount);
            return null;
        }
        AccountDto newAccount = null;

            Optional<Account> optionalAccount = accountRepository.findById(accountDto.getId());
            if(optionalAccount.isPresent()){
                Account account = optionalAccount.get();
                account.setAmount(account.getAmount() - amount);
                account = accountRepository.save(account);
                newAccount = modelMapper.map(account, AccountDto.class);
            }

        return newAccount;

    }

    @Override
    public double getBalance(AccountDto accountDto){
        try{
            if(accountDto != null && accountDto.getId() != null) {
                Optional<Account> account = accountRepository.findById(accountDto.getId());
                return account.map(Account::getAmount).orElse(null);
            }
        } catch (NullPointerException e){
            logger.error("Exception: {} during getBalance", 1, e);
        }

        return 0;
    }
}
