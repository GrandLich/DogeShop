package dev.mrlich.dogeshop.api;

import dev.mrlich.dogeshop.api.dto.SkinDto;
import dev.mrlich.dogeshop.api.dto.response.MessageResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "cart")
@RestController
@RequestMapping("/api/cart")
public interface CartApi {

    @ApiOperation(
            value = "Получить содержимое корзины текущего пользователя",
            nickname = "getCartContents",
            response = List.class,
            notes = "Для использования метода необходимо авторизоваться",
            tags = "getCartContents"
    )
    @ApiResponses({
            @ApiResponse(
                    code = 200,
                    message = "OK",
                    response = List.class
            ),
            @ApiResponse(
                    code = 403,
                    message = "Forbidden"
            )
    })
    @GetMapping("/")
    ResponseEntity<List<SkinDto>> getCartContents();

    @ApiOperation(
            value = "Добавить скин в корзину по ID",
            nickname = "addSkinToCart",
            response = MessageResponse.class,
            notes = "Для использования метода необходимо авторизоваться",
            tags = "addSkinToCart"
    )
    @ApiResponses({
            @ApiResponse(
                    code = 200,
                    message = "OK"
            ),
            @ApiResponse(
                    code = 400,
                    message = "Bad Request"
            ),
            @ApiResponse(
                    code = 403,
                    message = "Forbidden"
            )
    })
    @PostMapping("/add")
    ResponseEntity<MessageResponse> addSkinToCart(@RequestParam("skinId") Long skinId);

    @ApiOperation(
            value = "Очистить корзину",
            nickname = "clearCart",
            response = MessageResponse.class,
            notes = "Для использования метода необходимо авторизоваться",
            tags = "clearCart"
    )
    @ApiResponses({
            @ApiResponse(
                    code = 200,
                    message = "OK"
            ),
            @ApiResponse(
                    code = 403,
                    message = "Forbidden"
            )
    })
    @PostMapping("/clear")
    ResponseEntity<MessageResponse> clearCart();

    @ApiOperation(
            value = "Приобрести товары из корзины",
            nickname = "buy",
            response = MessageResponse.class,
            notes = "Для использования метода необходимо авторизоваться",
            tags = "buy"
    )
    @ApiResponses({
            @ApiResponse(
                    code = 200,
                    message = "OK",
                    response = MessageResponse.class
            ),
            @ApiResponse(
                    code = 403,
                    message = "Forbidden"
            )
    })
    @PostMapping("/buy")
    ResponseEntity<MessageResponse> buy();

}
