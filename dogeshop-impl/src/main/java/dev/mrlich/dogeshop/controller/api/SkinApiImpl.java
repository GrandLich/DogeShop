package dev.mrlich.dogeshop.controller.api;

import dev.mrlich.dogeshop.api.SkinApi;
import dev.mrlich.dogeshop.api.exception.SkinAlreadyExistsException;
import dev.mrlich.dogeshop.api.model.Cart;
import dev.mrlich.dogeshop.api.model.Skin;
import dev.mrlich.dogeshop.entity.SkinEntity;
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
    public ResponseEntity<Skin> getSkin(Long skinId) {
        Optional<SkinEntity> skinEntity = skinService.getSkin(skinId);
        if (skinEntity.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Skin skin = mapper.map(skinEntity.get(), Skin.class);
        return ResponseEntity.ok(skin);
    }

    @Override
    public ResponseEntity<Skin> createSkin(Skin skinDto) {
        SkinEntity skin = skinService.createSkin(skinDto);
        return ResponseEntity.status(201).body(mapper.map(skin, Skin.class));
    }

    @Override
    public ResponseEntity<Cart> addToCart(Long skinId) {
        return null;
    }

    @ExceptionHandler(SkinAlreadyExistsException.class)
    public ResponseEntity<Skin> handleSkinAlreadyExistsException() {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}
