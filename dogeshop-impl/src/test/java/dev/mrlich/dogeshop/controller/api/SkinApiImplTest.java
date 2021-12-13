package dev.mrlich.dogeshop.controller.api;

import dev.mrlich.dogeshop.api.dto.SkinDto;
import dev.mrlich.dogeshop.entity.Skin;
import dev.mrlich.dogeshop.service.SkinService;
import dev.mrlich.dogeshop.util.test.TestUtils;
import ma.glasnost.orika.MapperFacade;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SkinApiImplTest {

    @Mock
    private SkinService skinService;
    @Mock
    private MapperFacade mapper;

    @InjectMocks
    private SkinApiImpl skinApi;

    @Test
    void positiveGetSkinMustReturnValidSkinDto() {
        Long id = TestUtils.randomLong();
        SkinDto skinDto = generateSkinDto();

        when(skinService.getSkin(id)).thenReturn(Optional.of(generateSkin()));
        when(mapper.map(any(), any())).thenReturn(skinDto);

        ResponseEntity<SkinDto> actual = skinApi.getSkin(id);

        assertNotNull(actual);
        assertEquals(actual.getStatusCode(), HttpStatus.OK);
        assertEquals(actual.getBody(), skinDto);
    }

    @Test
    void negativeGetSkinMustReturnNotFound() {
        Long id = TestUtils.randomLong();

        when(skinService.getSkin(id)).thenReturn(Optional.empty());

        ResponseEntity<SkinDto> actual = skinApi.getSkin(id);

        assertNotNull(actual);
        assertEquals(actual.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    void createSkinMustReturnValidSkinDto() {
        SkinDto skinDto = generateSkinDto();

        when(skinService.createSkin(skinDto)).thenReturn(generateSkin());
        when(mapper.map(any(), any())).thenReturn(skinDto);

        ResponseEntity<SkinDto> actual = skinApi.createSkin(skinDto);

        assertNotNull(actual);
        assertEquals(actual.getStatusCode(), HttpStatus.CREATED);
        assertEquals(actual.getBody(), skinDto);
    }

    private Skin generateSkin() {
        Skin skin = new Skin();
        skin.setName(UUID.randomUUID().toString());

        return skin;
    }

    private SkinDto generateSkinDto() {
        SkinDto skinDto = new SkinDto();
        skinDto.setName(UUID.randomUUID().toString());

        return skinDto;
    }
}
