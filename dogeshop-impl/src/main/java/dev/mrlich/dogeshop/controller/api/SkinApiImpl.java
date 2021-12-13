package dev.mrlich.dogeshop.controller.api;

import dev.mrlich.dogeshop.api.SkinApi;
import dev.mrlich.dogeshop.api.dto.SkinDto;
import dev.mrlich.dogeshop.api.exception.SkinAlreadyExistsException;
import dev.mrlich.dogeshop.entity.Skin;
import dev.mrlich.dogeshop.service.SkinService;
import lombok.AllArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@AllArgsConstructor
public class SkinApiImpl implements SkinApi {

    private final SkinService skinService;
    private final MapperFacade mapper;

    @Override
    public ResponseEntity<SkinDto> getSkin(Long skinId) {
        Optional<Skin> skinEntity = skinService.getSkin(skinId);
        if (skinEntity.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        SkinDto skinDto = mapper.map(skinEntity.get(), SkinDto.class);
        return ResponseEntity.ok(skinDto);
    }

    @Override
    public ResponseEntity<SkinDto> createSkin(SkinDto skinDto) {
        Skin skin = skinService.createSkin(skinDto);
        return ResponseEntity.status(201).body(mapper.map(skin, SkinDto.class));
    }

    @ExceptionHandler(SkinAlreadyExistsException.class)
    public ResponseEntity<SkinDto> handleSkinAlreadyExistsException() {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}
