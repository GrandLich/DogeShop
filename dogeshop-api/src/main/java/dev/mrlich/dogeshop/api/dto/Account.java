package dev.mrlich.dogeshop.api.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Data
public class Account {

    private Long id;
    private String name;
    private BigDecimal balance;
    private Set<Skin> cartItems;
    private List<Order> orderHistory;

}
