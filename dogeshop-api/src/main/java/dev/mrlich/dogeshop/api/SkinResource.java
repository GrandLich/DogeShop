package dev.mrlich.dogeshop.api;

import dev.mrlich.dogeshop.api.dto.SkinDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/skin")
public interface SkinResource {

    @ApiOperation(value = "Получить скин по ID")
    @GetMapping("/{id}")
    SkinDto getSkin(@PathVariable("id") Long skinId);

    @ApiOperation("Создать скин")
    @PostMapping("/create")
    SkinDto createSkin(@RequestBody SkinDto skinDto);

}
