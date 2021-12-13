package dev.mrlich.dogeshop.controller.api;

import dev.mrlich.dogeshop.api.dto.request.LoginRequest;
import dev.mrlich.dogeshop.api.dto.response.LoginResponse;
import dev.mrlich.dogeshop.api.dto.response.LogoutResponse;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthApiImplTest {

    @Mock
    private AccountService accountService;
    @Mock
    private UserAuthentication authentication;

    @InjectMocks
    private AuthApiImpl authApi;

    @Test
    void loginShouldReturnForbiddenStatusWhenAlreadyAuthorized() {
        when(authentication.isLoggedIn()).thenReturn(true);

        ResponseEntity<LoginResponse> actual = authApi.login(generateLoginRequest());

        assertNotNull(actual);
        assertEquals(HttpStatus.FORBIDDEN, actual.getStatusCode());
    }

    @Test
    void loginShouldReturnBadRequestWhenLoginDataIsInvalid() {
        when(accountService.findByNameAndPassword(anyString(), anyString())).thenReturn(Optional.empty());

        ResponseEntity<LoginResponse> actual = authApi.login(generateLoginRequest());

        assertNotNull(actual);
        assertEquals(HttpStatus.BAD_REQUEST, actual.getStatusCode());
        assertNotNull(actual.getBody());
        assertEquals("Incorrect username or password", actual.getBody().getMessage());
    }

    @Test
    void loginShouldReturnCorrectDataIfSuccessful() {
        Account account = new Account();
        account.setName("Test");

        when(accountService.findByNameAndPassword(anyString(), anyString())).thenReturn(Optional.of(account));

        ResponseEntity<LoginResponse> actual = authApi.login(generateLoginRequest());

        assertNotNull(actual);
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertNotNull(actual.getBody());
        assertEquals(account.getName(), actual.getBody().getAccountName());
        verify(authentication).setCurrentAccount(account);
    }

    @Test
    void logoutShouldReturnForbiddenStatusWhenNotAuthorized() {
        ResponseEntity<LogoutResponse> actual = authApi.logout();

        assertNotNull(actual);
        assertEquals(HttpStatus.FORBIDDEN, actual.getStatusCode());
    }

    @Test
    void logoutShouldReturnCorrectDataIfSuccessful() {
        when(authentication.isLoggedIn()).thenReturn(true);

        ResponseEntity<LogoutResponse> actual = authApi.logout();

        assertNotNull(actual);
        assertEquals(HttpStatus.OK, actual.getStatusCode());
    }

    private LoginRequest generateLoginRequest() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setName(UUID.randomUUID().toString());
        loginRequest.setPassword(UUID.randomUUID().toString());

        return loginRequest;
    }


}
