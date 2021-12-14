package dev.mrlich.dogeshop.service;

import dev.mrlich.dogeshop.api.dto.Currency;

import java.math.BigDecimal;

public interface CurrencyService {

    BigDecimal getCurrencyRate(Currency currency);

}
