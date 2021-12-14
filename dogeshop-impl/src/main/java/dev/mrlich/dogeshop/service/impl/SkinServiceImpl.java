package dev.mrlich.dogeshop.service.impl;

import dev.mrlich.dogeshop.api.exception.SkinAlreadyExistsException;
import dev.mrlich.dogeshop.api.dto.SkinDto;
import dev.mrlich.dogeshop.entity.Skin;
import dev.mrlich.dogeshop.repository.SkinRepository;
import dev.mrlich.dogeshop.service.SkinService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SkinServiceImpl implements SkinService {

    private final SkinRepository skinRepository;

    @Override
    public Optional<Skin> getSkin(Long id) {
        return skinRepository.findById(id);
    }

    @Override
    public Optional<Skin> getSkin(String name) {
        return skinRepository.findByName(name);
    }

    @Override
    public Skin createSkin(SkinDto skinDto) {
        if (skinRepository.findByName(skinDto.getName()).isPresent()) {
            throw new SkinAlreadyExistsException(skinDto.getName());
        }
        Skin skin = new Skin();
        skin.setName(skinDto.getName());
        skin.setDescription(skinDto.getDescription());
        skin.setPrice(skinDto.getPrice());
        skin.setPictureUrl(skinDto.getPictureUrl());
        skin.setLocalizedName(skinDto.getLocalizedName());
        return skinRepository.save(skin);
    }

    @Override
    public void deleteSkin(Long id) {
        skinRepository.deleteById(id);
    }

    @Override
    public void updateSkin(Skin skin) {
        skinRepository.save(skin);
    }

    @Override
    public List<Skin> getAll() {
        return skinRepository.findAll();
    }

}
