package dev.mrlich.dogeshop.controller.api;

import dev.mrlich.dogeshop.api.CartApi;
import dev.mrlich.dogeshop.api.model.Skin;
import dev.mrlich.dogeshop.api.model.response.MessageResponse;
import dev.mrlich.dogeshop.auth.UserAuthentication;
import dev.mrlich.dogeshop.entity.OrderEntity;
import dev.mrlich.dogeshop.entity.SkinEntity;
import dev.mrlich.dogeshop.service.AccountService;
import dev.mrlich.dogeshop.service.OrderService;
import dev.mrlich.dogeshop.service.SkinService;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class CartApiImpl implements CartApi {

    private final UserAuthentication authentication;
    private final AccountService accountService;
    private final SkinService skinService;
    private final OrderService orderService;
    private final MapperFacade mapper;

    @Override
    public ResponseEntity<List<Skin>> getCartContents() {
        if (!authentication.isLoggedIn()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        Set<SkinEntity> skinsInCart = accountService.getSkinsInCart(authentication.getCurrentAccount());
        List<Skin> skins = mapSkinEntitySetToSkinsDtoList(skinsInCart);
        return ResponseEntity.ok(skins);
    }

    @Override
    public ResponseEntity<MessageResponse> addSkinToCart(@RequestParam("skinId") Long skinId) {
        if (!authentication.isLoggedIn()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        Optional<SkinEntity> skin = skinService.getSkin(skinId);
        if(skin.isEmpty()) {
            return ResponseEntity.ok(new MessageResponse().message("Скина с указанным ID не существует"));
        }
        accountService.addSkinToCart(authentication.getCurrentAccount(), skin.get());
        return ResponseEntity.ok(new MessageResponse().message("В корзину добавлен скин с ID "+ skinId));
    }

    @Override
    public ResponseEntity<MessageResponse> clearCart() {
        if (!authentication.isLoggedIn()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        accountService.clearCart(authentication.getCurrentAccount());
        return ResponseEntity.ok(new MessageResponse().message("Корзина очищена"));
    }

    @Override
    public ResponseEntity<MessageResponse> buy() {
        if (!authentication.isLoggedIn()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        Set<SkinEntity> skins = accountService.getSkinsInCart(authentication.getCurrentAccount());
        skins.forEach(skin -> {
            // TODO balance check
            OrderEntity order = orderService.createOrder(authentication.getCurrentAccount(), skin);
            accountService.addOrderToAccount(authentication.getCurrentAccount(), order);
        });
        accountService.clearCart(authentication.getCurrentAccount());
        return ResponseEntity.ok().build();
    }

    // TODO пиздец
    @Transactional
    private List<Skin> mapSkinEntitySetToSkinsDtoList(Set<SkinEntity> skinEntitySet) {
        List<Skin> skins = new ArrayList<>();
        skinEntitySet.forEach(skinEntity -> skins.add(mapper.map(skinEntity, Skin.class)));
        return skins;
    }
}
