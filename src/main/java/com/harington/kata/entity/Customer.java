package com.harington.kata.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name="customers")

public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(unique = true)
    private String email;

    private LocalDate birthday;

    private String username;

    private String password;

    private String role;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.REMOVE)
    private Set<Account> accounts;

}
