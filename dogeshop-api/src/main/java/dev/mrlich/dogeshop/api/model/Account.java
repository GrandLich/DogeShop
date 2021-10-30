package dev.mrlich.dogeshop.api.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class Account {

    private Long id;
    private String name;
    private BigDecimal balance;
    private Set<Skin> cartItems;

}
