package dev.mrlich.dogeshop.api;

import dev.mrlich.dogeshop.api.model.request.LoginRequest;
import dev.mrlich.dogeshop.api.model.response.LoginResponse;
import dev.mrlich.dogeshop.api.model.response.LogoutResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public interface AuthApi {

    @PostMapping("/login")
    ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest);

    @PostMapping("/logout")
    ResponseEntity<LogoutResponse> logout();

}
