package dev.mrlich.dogeshop.controller.api;

import dev.mrlich.dogeshop.api.AuthApi;
import dev.mrlich.dogeshop.api.model.request.LoginRequest;
import dev.mrlich.dogeshop.api.model.response.LoginResponse;
import dev.mrlich.dogeshop.api.model.response.LogoutResponse;
import dev.mrlich.dogeshop.auth.UserAuthentication;
import dev.mrlich.dogeshop.entity.AccountEntity;
import dev.mrlich.dogeshop.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class AuthApiImpl implements AuthApi {

    private final AccountService accountService;
    private final UserAuthentication authentication;

    @Override
    public ResponseEntity<LoginResponse> login(LoginRequest loginRequest) {
        if (authentication.isLoggedIn()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        Optional<AccountEntity> account = accountService.findByNameAndPassword(loginRequest.getName(), loginRequest.getPassword());
        if (account.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(new LoginResponse().message("Incorrect username or password"));
        }
        authentication.setCurrentAccount(account.get());
        return ResponseEntity.ok(new LoginResponse().accountName(account.get().getName()));
    }

    @Override
    public ResponseEntity<LogoutResponse> logout() {
        if (!authentication.isLoggedIn()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        authentication.setCurrentAccount(null);
        return ResponseEntity.ok().build();
    }
}
