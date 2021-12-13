package dev.mrlich.dogeshop.service;

import dev.mrlich.dogeshop.api.dto.SkinDto;
import dev.mrlich.dogeshop.entity.Skin;

import java.util.List;
import java.util.Optional;

public interface SkinService {

    Optional<Skin> getSkin(Long id);

    Optional<Skin> getSkin(String name);

    Skin createSkin(SkinDto skinDto);

    void deleteSkin(Long id);

    void updateSkin(Skin account);

    List<Skin> getAll();

}
