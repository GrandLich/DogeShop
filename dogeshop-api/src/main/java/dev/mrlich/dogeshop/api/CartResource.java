package dev.mrlich.dogeshop.api;

import dev.mrlich.dogeshop.api.dto.Currency;
import dev.mrlich.dogeshop.api.dto.SkinDto;
import dev.mrlich.dogeshop.api.dto.response.MessageResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RequestMapping("/api/cart")
public interface CartResource {

    @ApiOperation(
            value = "Получить содержимое корзины текущего пользователя",
            notes = "Для использования метода необходимо авторизоваться"
    )
    @GetMapping("/")
    List<SkinDto> getCartContents();

    @ApiOperation(
            value = "Добавить скин в корзину по ID",
            notes = "Для использования метода необходимо авторизоваться"
    )
    @PostMapping("/add")
    void addSkinToCart(@RequestParam("skinId") Long skinId);

    @ApiOperation(
            value = "Очистить корзину",
            notes = "Для использования метода необходимо авторизоваться"
    )
    @PostMapping("/clear")
    void clearCart();

    @ApiOperation(
            value = "Приобрести товары из корзины",
            notes = "Для использования метода необходимо авторизоваться"
    )
    @PostMapping("/buy")
    void buy();

    @ApiOperation("Получение коэффициента стоимости валюты относительно рубля")
    @GetMapping("/currencyRates/{currency}")
    BigDecimal currenciesRates(@PathVariable("currency") Currency currency);

}
