package com.harington.kata.service.impl;

import com.harington.kata.dto.AccountDto;
import com.harington.kata.entity.Account;
import com.harington.kata.repository.AccountRepository;
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

class AccountServiceImplTest {

    @InjectMocks
    private AccountServiceImpl accountServiceImpl ;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private ModelMapper modelMapper;

    @Test
    void testGetAccountById(){
        Account account = new Account() ;
        account.setId(1L);
        Mockito.when(this.accountRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(account));
        BDDMockito.given(this.modelMapper.map(account, AccountDto.class)).willReturn(new AccountDto());
        Assertions.assertNotNull(this.accountServiceImpl.getAccountById(1L));
    }

    @Test
    void testGetBalanceEquals(){
        Account accountDb = new Account() ;
        accountDb.setId(1L);
        accountDb.setAmount(20);

        AccountDto accountDto = new AccountDto() ;
        accountDto.setId(2L);
        accountDto.setAmount(20);

        Mockito.when(this.accountRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(accountDb));
        Assertions.assertEquals(this.accountServiceImpl.getBalance(accountDto), accountDb.getAmount());
    }

    @Test
    void testGetBalanceException(){
        AccountDto accountDto = new AccountDto() ;
        accountDto.setId(2L);
        accountDto.setAmount(20);

        Mockito.when(this.accountRepository.findById(Mockito.anyLong())).thenThrow(NullPointerException.class);
        Assertions.assertEquals(this.accountServiceImpl.getBalance(accountDto), 0);
    }

    @Test
    void testSave(){
        AccountDto accountDto = new AccountDto() ;
        accountDto.setId(1L);
        accountDto.setAmount(100);

        Account account = new Account() ;
        account.setId(1L);
        account.setAmount(100);

        Mockito.when(this.accountRepository.save(Mockito.any())).thenReturn(account);
        AccountServiceImpl accountService = new AccountServiceImpl(accountRepository, new ModelMapper());

        Assertions.assertNotNull(accountService.save(accountDto));
    }

    @Test
    void testDepositNumberZero(){
        Assertions.assertNull(this.accountServiceImpl.deposit (null, 0));
    }

    @Test
    void testDepositNumberNegative(){
        Assertions.assertNull(this.accountServiceImpl.deposit (null, -1));
    }


    @Test
    void testDepositAmount(){
        Account account = new Account() ;
        account.setId(1L);
        account.setAmount(110);

        AccountDto accountDto = new AccountDto() ;
        accountDto.setId(1L);
        accountDto.setAmount(100);

        Mockito.when(this.accountRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(account));
        Mockito.when(this.accountRepository.save(Mockito.any())).thenReturn(account);

        AccountServiceImpl accountService = new AccountServiceImpl(accountRepository, new ModelMapper());
        AccountDto result =  accountService.deposit(accountDto, 10);

        Assertions.assertEquals(result.getAmount(), 110);
    }

    @Test
    void testWithdrawalLessThanZero(){
        AccountDto accountDto = new AccountDto() ;
        accountDto.setId(1L);
        accountDto.setAmount(100);

        Assertions.assertNull(this.accountServiceImpl.withdrawal (accountDto, -1));
    }

    @Test
    void testWithdrawalAmountGreaterThanBalance(){
        Account account = new Account();
        account.setId(1L);
        account.setAmount(100);

        AccountDto accountDto = new AccountDto() ;
        accountDto.setId(1L);
        accountDto.setAmount(100);

        Mockito.when(this.accountRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(account));
        Assertions.assertNull(this.accountServiceImpl.withdrawal (accountDto, 200));
    }

    @Test
    void testWithdrawalAmountLessThanBalance(){
        Account accountDB = new Account();
        accountDB.setId(1L);
        accountDB.setAmount(100);

        Account accountSaved = new Account();
        accountSaved.setId(1L);
        accountSaved.setAmount(30);

        AccountDto accountDto = new AccountDto() ;
        accountDto.setId(1L);
        accountDto.setAmount(100);

        double amount = 70;

        Mockito.when(this.accountRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(accountDB));
        Mockito.when(this.accountRepository.save(Mockito.any())).thenReturn(accountSaved);

        AccountServiceImpl accountService = new AccountServiceImpl(accountRepository, new ModelMapper());
        AccountDto result =  accountService.withdrawal(accountDto, amount);

        Assertions.assertEquals(result.getAmount(), 30);
    }

}
