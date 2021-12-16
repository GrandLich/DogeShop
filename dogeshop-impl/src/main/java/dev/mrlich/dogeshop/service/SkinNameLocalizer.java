package dev.mrlich.dogeshop.service;

import java.util.Locale;

public interface SkinNameLocalizer {

    String localize(Long skinId, Locale locale);

}
