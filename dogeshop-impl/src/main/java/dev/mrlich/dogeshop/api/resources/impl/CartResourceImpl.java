package dev.mrlich.dogeshop.api.resources.impl;

import dev.mrlich.dogeshop.api.resource.CartResource;
import dev.mrlich.dogeshop.api.dto.Currency;
import dev.mrlich.dogeshop.api.dto.SkinDto;
import dev.mrlich.dogeshop.api.exception.EntityNotFoundException;
import dev.mrlich.dogeshop.api.exception.ActionIsNotAllowedException;
import dev.mrlich.dogeshop.api.exception.NotEnoughMoneyException;
import dev.mrlich.dogeshop.auth.UserAuthentication;
import dev.mrlich.dogeshop.entity.Order;
import dev.mrlich.dogeshop.entity.Skin;
import dev.mrlich.dogeshop.service.AccountService;
import dev.mrlich.dogeshop.service.CurrencyService;
import dev.mrlich.dogeshop.service.OrderService;
import dev.mrlich.dogeshop.service.SkinService;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class CartResourceImpl implements CartResource {

    private final UserAuthentication authentication;
    private final AccountService accountService;
    private final SkinService skinService;
    private final OrderService orderService;
    private final MapperFacade mapper;
    private final CurrencyService currencyService;

    @Override
    public List<SkinDto> getCartContents() {
        if (!authentication.isLoggedIn()) {
            throw new ActionIsNotAllowedException();
        }
        Set<Skin> skinsInCart = accountService.getSkinsInCart(authentication.getAccount().getId());
        return mapper.mapAsList(skinsInCart, SkinDto.class);
    }

    @Override
    public void addSkinToCart(@RequestParam("skinId") Long skinId) {
        if (!authentication.isLoggedIn()) {
            throw new ActionIsNotAllowedException();
        }
        Optional<Skin> skin = skinService.getSkin(skinId);
        if(skin.isEmpty()) {
            throw new EntityNotFoundException();
        }
        accountService.addSkinToCart(authentication.getAccount().getId(), skin.get());
    }

    @Override
    public void clearCart() {
        if (!authentication.isLoggedIn()) {
            throw new ActionIsNotAllowedException();
        }
        accountService.clearCart(authentication.getAccount().getId());
    }

    @Override
    public void buy() {
        if (!authentication.isLoggedIn()) {
            throw new ActionIsNotAllowedException();
        }
        Set<Skin> skins = accountService.getSkinsInCart(authentication.getAccount().getId());
        BigDecimal totalPrice = skins.stream()
                .map(Skin::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        if(totalPrice.compareTo(authentication.getAccount().getBalance()) > 0) {
            throw new NotEnoughMoneyException();
        }
        skins.forEach(skin -> {
            Order order = orderService.createOrder(authentication.getAccount(), skin);
            accountService.addOrderToAccount(authentication.getAccount().getId(), order);
        });
        accountService.setBalance(authentication.getAccount(), authentication.getAccount().getBalance().subtract(totalPrice));
        accountService.clearCart(authentication.getAccount().getId());
    }

    @Override
    public BigDecimal currenciesRates(@PathVariable("currency") Currency currency) {
        return currencyService.getCurrencyRate(currency);
    }
}
