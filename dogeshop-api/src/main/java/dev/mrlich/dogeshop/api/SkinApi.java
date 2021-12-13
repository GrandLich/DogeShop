package dev.mrlich.dogeshop.api;

import dev.mrlich.dogeshop.api.dto.SkinDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "skin")
@RestController
@RequestMapping("/api/skin")
public interface SkinApi {

    @ApiOperation(
            value = "Получить скин по ID",
            nickname = "getSkin",
            response = SkinDto.class,
            tags = "getSkin"
    )
    @ApiResponses({
            @ApiResponse(
                    code = 200,
                    message = "OK",
                    response = SkinDto.class
            ),
            @ApiResponse(
                    code = 404,
                    message = "Not Found"
            )
    })
    @GetMapping("/{id}")
    ResponseEntity<SkinDto> getSkin(@PathVariable("id") Long skinId);

    @ApiOperation(
            value = "Создать скин",
            notes = "createSkin",
            response = SkinDto.class,
            tags = "createSkin"
    )
    @ApiResponses({
            @ApiResponse(
                    code = 201,
                    message = "Created",
                    response = SkinDto.class
            )
    })
    @PostMapping("/create")
    ResponseEntity<SkinDto> createSkin(@RequestBody SkinDto skinDto);

}
