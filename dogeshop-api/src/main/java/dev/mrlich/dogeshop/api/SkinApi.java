package dev.mrlich.dogeshop.api;

import dev.mrlich.dogeshop.api.dto.SkinDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/skin")
public interface SkinApi {

    @GetMapping("/{id}")
    ResponseEntity<SkinDto> getSkin(@PathVariable("id") Long skinId);

    @PostMapping("/create")
    ResponseEntity<SkinDto> createSkin(@RequestBody SkinDto skinDto);

}
