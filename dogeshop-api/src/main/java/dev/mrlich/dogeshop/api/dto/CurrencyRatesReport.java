package dev.mrlich.dogeshop.api.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Data
public class CurrencyRatesReport {

    private String disclaimer;
    private LocalDate date;
    private Instant timestamp;
    private Currency base;
    private Map<Currency, BigDecimal> rates = new HashMap<>();

}
