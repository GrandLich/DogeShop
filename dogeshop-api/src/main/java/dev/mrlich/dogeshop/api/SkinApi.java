package dev.mrlich.dogeshop.api;

import dev.mrlich.dogeshop.api.model.Cart;
import dev.mrlich.dogeshop.api.model.Skin;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/skin")
public interface SkinApi {

    @GetMapping("/{id}")
    ResponseEntity<Skin> getSkin(@RequestParam("id") Long skinId);

    @PostMapping("/create")
    ResponseEntity<Skin> createSkin(@RequestBody Skin skin);

    @PostMapping("/addToCart")
    ResponseEntity<Cart> addToCart(@RequestParam Long skinId);

}
