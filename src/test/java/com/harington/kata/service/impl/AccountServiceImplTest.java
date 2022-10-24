package com.harington.kata.service.impl;

import com.harington.kata.entity.Account;
import com.harington.kata.repository.AccountRepository;
import com.harington.kata.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)

class AccountServiceImplTest {

    AccountServiceImpl accountService;
    CustomerServiceImpl customerService;

    @Mock AccountRepository accountRepository;
    @Mock CustomerRepository customerRepository;
    @Mock ModelMapper modelMapper;

    Long accountId = 1L;
    double negativeAmount = -33;
    double positiveAmount = 33;
    double zeroAmount = 0;

    @BeforeEach
    void setUp(){
        accountService = new AccountServiceImpl(accountRepository, customerService, modelMapper);
        customerService = new CustomerServiceImpl(customerRepository, modelMapper);
    }

    @Test
    void testGetAccountById(){
        accountService.getAccountById(anyLong());
        verify(accountRepository).findById(anyLong());
    }

    @Test
    void testGetBalance(){
        Account account = new Account();
        account.setId(accountId);

        when(accountRepository.findById(anyLong())).thenReturn(Optional.of(account));
        accountService.getBalance(accountId);
        verify(accountRepository).findById(accountId);
    }

    @Test
    void testGetBalanceWhenThrowException(){
        given(accountRepository.findById(accountId)).willReturn(Optional.empty());

        assertThatThrownBy(() -> accountService.getBalance(accountId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Account id: " + accountId + " not found");

        verify(accountRepository, times(1)).findById(accountId);
    }

    @Test
    void testDepositNumberZero(){
        assertThatThrownBy(() -> accountService.deposit(accountId, zeroAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Amount " + zeroAmount + " is negative or Zero");

        verify(accountRepository, never()).save(any());
    }

    @Test
    void testDepositNumberNegative(){

        assertThatThrownBy(() -> accountService.deposit(accountId, negativeAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Amount " + negativeAmount + " is negative or Zero");

        verify(accountRepository, never()).save(any());
    }


    @Test
    void testDepositNumberPositive(){

        Account account = new Account();
        account.setId(accountId);

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        accountService.deposit(accountId, positiveAmount);

        ArgumentCaptor<Account> accountArgumentCaptor = ArgumentCaptor.forClass(Account.class);
        verify(accountRepository).save(accountArgumentCaptor.capture());
        Account capturedAccount = accountArgumentCaptor.getValue();
        assertThat(capturedAccount).isEqualTo(account);
    }

    @Test
    void testWithdrawalAmountGreaterThanBalance(){
        double balance = 100;
        double amountGreater = 500;

        Account account = new Account();
        account.setId(accountId);
        account.setAmount(balance);

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        assertThatThrownBy(() -> accountService.withdrawal(accountId, amountGreater))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Amount is greater than Balance");

        verify(accountRepository, times(1)).findById(accountId);
        verify(accountRepository, never()).save(account);
    }

    @Test
    void testWithdrawalAmountLessThanBalance(){
        double balance = 500;
        double withdrawalAmount = 100;

        Account account = new Account();
        account.setId(accountId);
        account.setAmount(balance);

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        accountService.withdrawal(accountId, withdrawalAmount);

        ArgumentCaptor<Account> accountArgumentCaptor = ArgumentCaptor.forClass(Account.class);
        verify(accountRepository).save(accountArgumentCaptor.capture());
        Account capturedAccount = accountArgumentCaptor.getValue();
        assertThat(capturedAccount).isEqualTo(account);
    }

}
