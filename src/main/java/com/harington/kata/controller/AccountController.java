package com.harington.kata.controller;

import com.harington.kata.dto.AccountDto;
import com.harington.kata.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/account/{id}")
    public ResponseEntity<AccountDto> getByAccountId(@PathVariable("id") Long id) {
        AccountDto accountDto = accountService.getAccountById(id);
        if (accountDto != null) {
            return new ResponseEntity<>(accountDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/accounts-by-customer/{id}")
    public ResponseEntity<Set<AccountDto>> getAllAccounts(@PathVariable("id") Long customerId) {
        Set<AccountDto> accountDtos = accountService.getAllAccountsByCustomer(customerId);

        if (accountDtos != null && accountDtos.size() > 0) {
            return new ResponseEntity<>(accountDtos, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/account/withdrawal")
    public ResponseEntity<AccountDto> withdrawal(@RequestParam AccountDto accountParamDto,
                                                 @RequestParam double amount) {
        try {
            AccountDto accountDto = accountService.withdrawal(accountParamDto, amount);
            //Amount greater than balance
            if(accountDto == null){
                return new ResponseEntity<>(accountDto, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(accountDto, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/account/deposit")
    public ResponseEntity<AccountDto> deposit(@RequestParam AccountDto accountParamDto,
                                                 @RequestParam double amount) {
        try {
            AccountDto accountDto = accountService.deposit(accountParamDto, amount);
            return new ResponseEntity<>(accountDto, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
