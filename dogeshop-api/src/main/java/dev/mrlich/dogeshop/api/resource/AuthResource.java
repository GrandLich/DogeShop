package dev.mrlich.dogeshop.api.resource;

import dev.mrlich.dogeshop.api.dto.request.LoginRequest;
import dev.mrlich.dogeshop.api.dto.response.LoginResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/auth")
public interface AuthResource {

    @ApiOperation("Авторизоваться")
    @PostMapping("/login")
    LoginResponse login(@RequestBody LoginRequest loginRequest);

    @ApiOperation(
            value = "Выйти из аккаунта",
            notes = "Для использования метода необходимо авторизоваться"
    )
    @PostMapping("/logout")
    void logout();

}
