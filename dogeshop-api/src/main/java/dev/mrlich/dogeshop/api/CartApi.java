package dev.mrlich.dogeshop.api;

import dev.mrlich.dogeshop.api.dto.SkinDto;
import dev.mrlich.dogeshop.api.dto.response.MessageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public interface CartApi {

    @GetMapping("/")
    ResponseEntity<List<SkinDto>> getCartContents();

    @PostMapping("/add")
    ResponseEntity<MessageResponse> addSkinToCart(@RequestParam("skinId") Long skinId);

    @PostMapping("/clear")
    ResponseEntity<MessageResponse> clearCart();

    @PostMapping("/buy")
    ResponseEntity<MessageResponse> buy();

}
