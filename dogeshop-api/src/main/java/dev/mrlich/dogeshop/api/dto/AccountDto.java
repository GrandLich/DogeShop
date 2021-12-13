package dev.mrlich.dogeshop.api.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Data
public class AccountDto {

    private Long id;
    private String name;
    private BigDecimal balance;
    private Set<SkinDto> cartItems;
    private List<OrderDto> orderDtoHistory;

}
