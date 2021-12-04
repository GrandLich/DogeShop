package dev.mrlich.dogeshop.service;

import dev.mrlich.dogeshop.api.model.Skin;
import dev.mrlich.dogeshop.entity.SkinEntity;

import java.util.List;
import java.util.Optional;

public interface SkinService {

    Optional<SkinEntity> getSkin(Long id);

    Optional<SkinEntity> getSkin(String name);

    SkinEntity createSkin(Skin skinDto);

    void deleteSkin(Long id);

    void updateSkin(SkinEntity account);

    List<SkinEntity> getAll();

}
