package dev.mrlich.dogeshop.controller.api;

import dev.mrlich.dogeshop.api.dto.request.LoginRequest;
import dev.mrlich.dogeshop.api.dto.response.LoginResponse;
import dev.mrlich.dogeshop.api.exception.ActionIsNotAllowedException;
import dev.mrlich.dogeshop.api.exception.AuthenticationException;
import dev.mrlich.dogeshop.auth.UserAuthentication;
import dev.mrlich.dogeshop.entity.Account;
import dev.mrlich.dogeshop.service.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthResourceImplTest {

    @Mock
    private AccountService accountService;
    @Mock
    private UserAuthentication authentication;

    @InjectMocks
    private AuthResourceImpl authApi;

    @Test
    void loginShouldThrowActionIsNotAllowedExceptionWhenAlreadyAuthorized() {
        when(authentication.isLoggedIn()).thenReturn(true);

        assertThrows(ActionIsNotAllowedException.class, () -> authApi.login(generateLoginRequest()));
    }

    @Test
    void loginShouldThrowAuthenticationExceptionWhenLoginDataIsInvalid() {
        when(accountService.findByNameAndPassword(anyString(), anyString())).thenReturn(Optional.empty());

        assertThrows(AuthenticationException.class, () -> authApi.login(generateLoginRequest()));
    }

    @Test
    void loginShouldReturnCorrectAccountNameAndCallSwitchMethodIfSuccessful() {
        Account account = new Account();
        account.setName("Test");

        when(accountService.findByNameAndPassword(anyString(), anyString())).thenReturn(Optional.of(account));

        LoginResponse actual = authApi.login(generateLoginRequest());

        assertNotNull(actual);
        assertEquals(account.getName(), actual.getAccountName());
        verify(authentication).switchAccount(account);
    }

    @Test
    void logoutShouldThrowActionIsNotAllowedExceptionWhenNotAuthorized() {
        assertThrows(ActionIsNotAllowedException.class, () -> authApi.logout());
    }

    @Test
    void logoutShouldCallSwitchAccountMethodIfSuccessful() {
        when(authentication.isLoggedIn()).thenReturn(true);

        authApi.logout();

        verify(authentication).switchAccount(null);
    }

    private LoginRequest generateLoginRequest() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setName(UUID.randomUUID().toString());
        loginRequest.setPassword(UUID.randomUUID().toString());

        return loginRequest;
    }


}
