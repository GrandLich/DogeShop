package dev.mrlich.dogeshop.api.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
public class Order {

    private Long id;
    private String skinName;
    private Instant date;
    private BigDecimal price;
    private Long accountId;

}
