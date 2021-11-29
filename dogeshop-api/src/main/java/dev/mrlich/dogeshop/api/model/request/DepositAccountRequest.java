package dev.mrlich.dogeshop.api.model.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DepositAccountRequest {

    private BigDecimal balance;

}
