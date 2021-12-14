package dev.mrlich.dogeshop.controller.api;

import dev.mrlich.dogeshop.api.AuthResource;
import dev.mrlich.dogeshop.api.dto.request.LoginRequest;
import dev.mrlich.dogeshop.api.dto.response.LoginResponse;
import dev.mrlich.dogeshop.api.exception.ActionIsNotAllowedException;
import dev.mrlich.dogeshop.api.exception.AuthenticationException;
import dev.mrlich.dogeshop.auth.UserAuthentication;
import dev.mrlich.dogeshop.entity.Account;
import dev.mrlich.dogeshop.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class AuthResourceImpl implements AuthResource {

    private final AccountService accountService;
    private final UserAuthentication authentication;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        if (authentication.isLoggedIn()) {
            throw new ActionIsNotAllowedException();
        }
        Optional<Account> account = accountService.findByNameAndPassword(loginRequest.getName(), loginRequest.getPassword());
        if (account.isEmpty()) {
            throw new AuthenticationException();
        }
        authentication.switchAccount(account.get());
        return new LoginResponse().accountName(account.get().getName());
    }

    @Override
    public void logout() {
        if (!authentication.isLoggedIn()) {
            throw new ActionIsNotAllowedException();
        }
        authentication.switchAccount(null);
    }
}
