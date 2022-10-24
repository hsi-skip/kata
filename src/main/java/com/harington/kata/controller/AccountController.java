package com.harington.kata.controller;

import com.harington.kata.dto.AccountDto;
import com.harington.kata.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/account/{id}")
    public ResponseEntity<AccountDto> getByAccountId(@PathVariable("id") Long id) {
        return accountService.getAccountById(id).map(accountDto -> ResponseEntity.ok(accountDto))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/account/withdrawal")
    public ResponseEntity<String> withdrawal(@RequestParam Long accountId, @RequestParam double amount) {
        accountService.withdrawal(accountId, amount);
        return ResponseEntity.ok("withdrawal successful");
    }

    @PutMapping("/account/deposit")
    public ResponseEntity<String> deposit(@RequestParam long accountId,
                                                 @RequestParam double amount) {
            accountService.deposit(accountId, amount);
            return ResponseEntity.ok("deposit successful");
    }

}
