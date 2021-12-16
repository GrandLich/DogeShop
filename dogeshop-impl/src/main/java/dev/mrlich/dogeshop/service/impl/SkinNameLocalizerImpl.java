package dev.mrlich.dogeshop.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.mrlich.dogeshop.api.dto.SkinNameLocaleSet;
import dev.mrlich.dogeshop.entity.Skin;
import dev.mrlich.dogeshop.service.SkinNameLocalizer;
import dev.mrlich.dogeshop.service.SkinService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SkinNameLocalizerImpl implements SkinNameLocalizer {

    private final SkinService skinService;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    public String localize(Long skinId, Locale locale) {
        Optional<Skin> skin = skinService.getSkin(skinId);
        if (skin.isEmpty()) {
            return "NULL";
        }
        SkinNameLocaleSet skinNameLocaleSet = objectMapper.reader().readValue(skin.get().getLocalizedName(), SkinNameLocaleSet.class);
        if (locale.getLanguage().equals("ru")) {
            return skinNameLocaleSet.getRu();
        } else if (locale.getLanguage().equals("en")) {
            return skinNameLocaleSet.getEn();
        }
        return skin.get().getName();
    }
}
