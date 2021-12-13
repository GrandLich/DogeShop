package dev.mrlich.dogeshop.api;

import dev.mrlich.dogeshop.api.dto.SkinDto;
import dev.mrlich.dogeshop.api.dto.request.LoginRequest;
import dev.mrlich.dogeshop.api.dto.response.LoginResponse;
import dev.mrlich.dogeshop.api.dto.response.LogoutResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value =  "auth")
@RestController
@RequestMapping("/api/auth")
public interface AuthApi {

    @ApiOperation(
            value = "Авторизоваться",
            nickname = "login",
            response = LoginResponse.class,
            tags = "login"
    )
    @ApiResponses({
            @ApiResponse(
                    code = 200,
                    message = "OK",
                    response = LoginResponse.class
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
    @PostMapping("/login")
    ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest);

    @ApiOperation(
            value = "Выйти из аккаунта",
            nickname = "logout",
            response = LogoutResponse.class,
            notes = "Для использования метода необходимо авторизоваться",
            tags = "logout"
    )
    @ApiResponses({
            @ApiResponse(
                    code = 200,
                    message = "OK",
                    response = LogoutResponse.class
            ),
            @ApiResponse(
                    code = 403,
                    message = "Forbidden"
            )
    })
    @PostMapping("/logout")
    ResponseEntity<LogoutResponse> logout();

}
