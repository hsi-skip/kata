package com.harington.kata.repository;

import com.harington.kata.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Account save(Account account);

}
