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
import java.math.BigDecimal;
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
        List<Skin> skins = mapper.mapAsList(skinsInCart, Skin.class);
        return ResponseEntity.ok(skins);
    }

    @Override
    public ResponseEntity<MessageResponse> addSkinToCart(@RequestParam("skinId") Long skinId) {
        if (!authentication.isLoggedIn()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        Optional<SkinEntity> skin = skinService.getSkin(skinId);
        if(skin.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        accountService.addSkinToCart(authentication.getCurrentAccount(), skin.get());
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<MessageResponse> clearCart() {
        if (!authentication.isLoggedIn()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        accountService.clearCart(authentication.getCurrentAccount());
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<MessageResponse> buy() {
        if (!authentication.isLoggedIn()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        Set<SkinEntity> skins = accountService.getSkinsInCart(authentication.getCurrentAccount());
        BigDecimal totalPrice = skins.stream()
                .map(SkinEntity::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        if(totalPrice.compareTo(authentication.getCurrentAccount().getBalance()) > 0) {
            return ResponseEntity.ok(new MessageResponse().message("Not enough money"));
        }
        skins.forEach(skin -> {
            OrderEntity order = orderService.createOrder(authentication.getCurrentAccount(), skin);
            accountService.addOrderToAccount(authentication.getCurrentAccount(), order);
        });
        accountService.setBalance(authentication.getCurrentAccount(), authentication.getCurrentAccount().getBalance().subtract(totalPrice));
        accountService.clearCart(authentication.getCurrentAccount());
        return ResponseEntity.ok().build();
    }
}
