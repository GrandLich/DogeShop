package dev.mrlich.dogeshop.service;

import dev.mrlich.dogeshop.api.model.Skin;
import dev.mrlich.dogeshop.entity.SkinEntity;

import javax.transaction.Transactional;
import java.util.Optional;

public interface SkinService {

    Optional<SkinEntity> getSkin(Long id);

    Optional<SkinEntity> getSkin(String name);

    @Transactional
    SkinEntity createSkin(Skin skinDto);

    @Transactional
    void deleteSkin(Long id);

    @Transactional
    void updateSkin(SkinEntity account);

}
