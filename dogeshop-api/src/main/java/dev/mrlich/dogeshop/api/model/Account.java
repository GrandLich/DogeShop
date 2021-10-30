package dev.mrlich.dogeshop.api.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Account {

    private Long id;
    private String name;
    private BigDecimal balance;
    private Cart cart;

}