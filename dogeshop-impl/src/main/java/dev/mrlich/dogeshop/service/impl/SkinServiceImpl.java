package dev.mrlich.dogeshop.service.impl;

import dev.mrlich.dogeshop.api.exception.SkinAlreadyExistsException;
import dev.mrlich.dogeshop.api.dto.Skin;
import dev.mrlich.dogeshop.entity.SkinEntity;
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
    public Optional<SkinEntity> getSkin(Long id) {
        return skinRepository.findById(id);
    }

    @Override
    public Optional<SkinEntity> getSkin(String name) {
        return skinRepository.findByName(name);
    }

    @Override
    public SkinEntity createSkin(Skin skinDto) {
        if (skinRepository.findByName(skinDto.getName()).isPresent()) {
            throw new SkinAlreadyExistsException(skinDto.getName());
        }
        SkinEntity skin = new SkinEntity();
        skin.setName(skinDto.getName());
        skin.setDescription(skinDto.getDescription());
        skin.setPrice(skinDto.getPrice());
        skin.setPictureUrl(skinDto.getPictureUrl());
        return skinRepository.save(skin);
    }

    @Override
    public void deleteSkin(Long id) {
        skinRepository.deleteById(id);
    }

    @Override
    public void updateSkin(SkinEntity skin) {
        skinRepository.save(skin);
    }

    @Override
    public List<SkinEntity> getAll() {
        List<SkinEntity> skins = new ArrayList<>();
        skinRepository.findAll().forEach(skins::add);
        return skins;
    }

}
