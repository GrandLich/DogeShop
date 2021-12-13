package dev.mrlich.dogeshop.service.impl;

import dev.mrlich.dogeshop.api.dto.SkinDto;
import dev.mrlich.dogeshop.api.exception.SkinAlreadyExistsException;
import dev.mrlich.dogeshop.entity.Skin;
import dev.mrlich.dogeshop.repository.SkinRepository;
import dev.mrlich.dogeshop.util.test.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SkinServiceImplTest {

    @Mock
    private SkinRepository skinRepository;

    @InjectMocks
    private SkinServiceImpl service;

    @Test
    void getSkinMustCallFindByIdMethod() {
        Long id = TestUtils.randomLong();

        service.getSkin(id);

        verify(skinRepository).findById(id);
    }

    @Test
    void getSkinMustCallFindByNameMethod() {
        String randomName = UUID.randomUUID().toString();

        service.getSkin(randomName);

        verify(skinRepository).findByName(randomName);
    }

    @Test
    void positiveCreateSkinMustReturnCorrectSkin() {
        SkinDto skinDto = generateSkinDto();

        Skin expected = new Skin();
        expected.setName(skinDto.getName());
        expected.setDescription(skinDto.getDescription());

        when(skinRepository.findByName(anyString())).thenReturn(Optional.empty());
        when(skinRepository.save(any())).thenReturn(expected);

        Skin actual = service.createSkin(skinDto);

        assertNotNull(actual);
        assertEquals(actual, expected);
    }

    @Test
    void positiveCreateSkinMustCallSaveMethod() {
        SkinDto skinDto = generateSkinDto();

        when(skinRepository.findByName(anyString())).thenReturn(Optional.empty());

        service.createSkin(skinDto);

        verify(skinRepository).save(any());
    }

    @Test
    void negativeCreateSkinMustThrowSkinAlreadyExistsException() {
        SkinDto skinDto = generateSkinDto();

        when(skinRepository.findByName(anyString())).thenReturn(Optional.of(new Skin()));

        assertThrows(SkinAlreadyExistsException.class,
                () -> service.createSkin(skinDto));
    }

    @Test
    void deleteSkinMustCallRepositoryMethod() {
        Long id = TestUtils.randomLong();

        service.deleteSkin(id);

        verify(skinRepository).deleteById(id);
    }

    @Test
    void updateSkinMustCallSaveMethod() {
        Skin skin = new Skin();

        service.updateSkin(skin);

        verify(skinRepository).save(skin);
    }

    @Test
    void getAllMustCallRepositoryMethod() {
        when(service.getAll()).thenReturn(Collections.emptyList());

        service.getAll();

        verify(skinRepository).findAll();
    }

    private SkinDto generateSkinDto() {
        SkinDto skinDto = new SkinDto();
        skinDto.setName(UUID.randomUUID().toString());
        skinDto.setDescription(UUID.randomUUID().toString());

        return skinDto;
    }
}
