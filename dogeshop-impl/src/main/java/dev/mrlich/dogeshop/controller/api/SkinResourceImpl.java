package dev.mrlich.dogeshop.controller.api;

import dev.mrlich.dogeshop.api.SkinResource;
import dev.mrlich.dogeshop.api.dto.SkinDto;
import dev.mrlich.dogeshop.api.exception.EntityNotFoundException;
import dev.mrlich.dogeshop.api.exception.SkinAlreadyExistsException;
import dev.mrlich.dogeshop.entity.Skin;
import dev.mrlich.dogeshop.service.SkinService;
import lombok.AllArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@AllArgsConstructor
public class SkinResourceImpl implements SkinResource {

    private final SkinService skinService;
    private final MapperFacade mapper;

    @Override
    public SkinDto getSkin(Long skinId) {
        Optional<Skin> skinEntity = skinService.getSkin(skinId);
        if (skinEntity.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return mapper.map(skinEntity.get(), SkinDto.class);
    }

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    public SkinDto createSkin(SkinDto skinDto) {
        Skin skin = skinService.createSkin(skinDto);
        return mapper.map(skin, SkinDto.class);
    }

    @ExceptionHandler(SkinAlreadyExistsException.class)
    public ResponseEntity<SkinDto> handleSkinAlreadyExistsException() {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}
