package dev.mrlich.dogeshop.service.impl;

import dev.mrlich.dogeshop.api.dto.Currency;
import dev.mrlich.dogeshop.api.dto.CurrencyRatesReport;
import dev.mrlich.dogeshop.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

    private static final String URL = "https://www.cbr-xml-daily.ru/latest.js";
    private final RestTemplate restTemplate;

    @Override
    public BigDecimal getCurrencyRate(Currency currency) {
        if(currency == Currency.RUB) {
            return BigDecimal.ONE;
        }
        CurrencyRatesReport report = restTemplate.getForObject(URL, CurrencyRatesReport.class);
        return report.getRates().get(currency);
    }
}
