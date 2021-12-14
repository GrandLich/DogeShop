package dev.mrlich.dogeshop.api.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;

public enum Currency implements Serializable {

    RUB("RUB"),
    USD("USD"),
    UAH("UAH"),
    EUR("EUR"),
    JPY("JPY"),
    AUD("AUD"),
    AZN("AZN"),
    GBP("GBP"),
    AMD("AMD"),
    BYN("BYN"),
    BGN("BGN"),
    BRL("BRL"),
    HUF("HUF"),
    HKD("HKD"),
    DKK("DKK"),
    INR("INR"),
    KZT("KZT"),
    CAD("CAD"),
    KGS("KGS"),
    CNY("CNY"),
    MDL("MDL"),
    NOK("NOK"),
    PLN("PLN"),
    RON("RON"),
    XDR("XDR"),
    SGD("SGD"),
    TJS("TJS"),
    TRY("TRY"),
    TMT("TMT"),
    UZS("UZS"),
    CZK("CZK"),
    SEK("SEK"),
    CHF("CHF"),
    ZAR("ZAR"),
    KRW("KRW")
    ;

    @JsonProperty("code")
    String code;

    Currency(String code) {
        this.code = code;
    }

    @JsonCreator
    public Currency fromCode(String code) {
        return Currency.valueOf(code);
    }

    @JsonValue
    public String getCode() {
        return code;
    }
}
