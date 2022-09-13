package com.harington.kata.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter

public class CustomerDto {
        private Long id;
        private String firstName;
        private String lastName;
        private String email;
        private LocalDate birthday;
        private String username;
        private String password;
        private String role;
        private Set<AccountDto> accounts;
}
