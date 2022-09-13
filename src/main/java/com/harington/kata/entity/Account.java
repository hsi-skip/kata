package com.harington.kata.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="accounts")

public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String rib;
    private String swift;
    private String iban;
    private double amount;

    @ManyToOne()
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
