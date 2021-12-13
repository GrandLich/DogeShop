package dev.mrlich.dogeshop.controller.api;

import dev.mrlich.dogeshop.api.dto.AccountDto;
import dev.mrlich.dogeshop.api.dto.OrderDto;
import dev.mrlich.dogeshop.api.dto.request.CreateAccountRequest;
import dev.mrlich.dogeshop.api.dto.request.DepositAccountRequest;
import dev.mrlich.dogeshop.api.dto.response.LogoutResponse;
import dev.mrlich.dogeshop.auth.UserAuthentication;
import dev.mrlich.dogeshop.entity.Account;
import dev.mrlich.dogeshop.service.AccountService;
import dev.mrlich.dogeshop.service.OrderService;
import dev.mrlich.dogeshop.util.test.TestUtils;
import liquibase.pro.packaged.C;
import ma.glasnost.orika.MapperFacade;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountApiImplTest {

    @Mock
    private MapperFacade mapper;
    @Mock
    private AccountService accountService;
    @Mock
    private OrderService orderService;
    @Mock
    private UserAuthentication authentication;

    @InjectMocks
    private AccountApiImpl accountApi;

    @Test
    void getAccountShouldReturnNotFoundIfAccountNotFound() {
        Long id = TestUtils.randomLong();

        when(accountService.getAccount(id)).thenReturn(Optional.empty());

        ResponseEntity<AccountDto> actual = accountApi.getAccount(id);

        assertNotNull(actual);
        assertEquals(HttpStatus.NOT_FOUND, actual.getStatusCode());
    }

    @Test
    void getAccountShouldReturnOkIfSuccessful() {
        Long id = TestUtils.randomLong();
        Account account = generateAccount();

        when(accountService.getAccount(id)).thenReturn(Optional.of(account));
        when(mapper.map(account, AccountDto.class)).thenReturn(generateAccountDto());

        ResponseEntity<AccountDto> actual = accountApi.getAccount(id);

        assertNotNull(actual);
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        verify(mapper).map(account, AccountDto.class);
    }

    @Test
    void createAccountShouldReturnCorrectAccount() {
        CreateAccountRequest createAccountRequest = generateCreateAccountRequest();
        Account account = generateAccount();

        when(accountService.createAccount(createAccountRequest.getName(), createAccountRequest.getPassword()))
                .thenReturn(account);
        when(mapper.map(account, AccountDto.class)).thenReturn(generateAccountDto());

        ResponseEntity<AccountDto> actual = accountApi.createAccount(createAccountRequest);

        assertNotNull(actual);
        assertEquals(HttpStatus.CREATED, actual.getStatusCode());
        verify(accountService).createAccount(createAccountRequest.getName(), createAccountRequest.getPassword());
        verify(mapper).map(account, AccountDto.class);
    }

    @Test
    void depositAccountShouldReturnForbiddenIfNotAuthorized() {
        DepositAccountRequest depositAccountRequest = generateDepositAccountRequest();

        ResponseEntity<Void> actual = accountApi.depositAccount(depositAccountRequest);

        assertNotNull(actual);
        assertEquals(HttpStatus.FORBIDDEN, actual.getStatusCode());
    }

    @Test
    void depositAccountShouldCallServiceMethod() {
        DepositAccountRequest depositAccountRequest = generateDepositAccountRequest();
        Account account = generateAccount();

        when(authentication.isLoggedIn()).thenReturn(true);
        when(authentication.getCurrentAccount()).thenReturn(account);

        ResponseEntity<Void> actual = accountApi.depositAccount(depositAccountRequest);

        assertNotNull(actual);
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        verify(accountService).setBalance(eq(account), any());
    }

    @Test
    void getAccountOrdersShouldReturnNotFoundIfAccountNotFound() {
        Long id = TestUtils.randomLong();

        when(accountService.getAccount(id)).thenReturn(Optional.empty());

        ResponseEntity<List<OrderDto>> actual = accountApi.getAccountOrders(id);

        assertNotNull(actual);
        assertEquals(HttpStatus.NOT_FOUND, actual.getStatusCode());
    }

    @Test
    void getAccountOrdersShouldReturnOrderListFromOrderService() {
        Long id = TestUtils.randomLong();
        Account account = generateAccount();

        when(accountService.getAccount(id)).thenReturn(Optional.of(account));
        when(orderService.getOrders(account)).thenReturn(Collections.emptyList());
        when(mapper.mapAsList(anyIterable(), eq(OrderDto.class))).thenReturn(Collections.emptyList());

        ResponseEntity<List<OrderDto>> actual = accountApi.getAccountOrders(id);

        assertNotNull(actual);
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        verify(orderService).getOrders(account);
        verify(mapper).mapAsList(anyIterable(), eq(OrderDto.class));
    }

    private Account generateAccount() {
        Account account = new Account();
        account.setName("Vasya Pupkin");
        account.setBalance(BigDecimal.ZERO);

        return account;
    }

    private AccountDto generateAccountDto() {
        AccountDto accountDto = new AccountDto();
        accountDto.setName("Vasya Pupkin");
        accountDto.setBalance(BigDecimal.ZERO);

        return accountDto;
    }

    private CreateAccountRequest generateCreateAccountRequest() {
        CreateAccountRequest createAccountRequest = new CreateAccountRequest();
        createAccountRequest.setName("Vasya Pupkin");
        createAccountRequest.setPassword(UUID.randomUUID().toString());

        return createAccountRequest;
    }

    private DepositAccountRequest generateDepositAccountRequest() {
        DepositAccountRequest depositAccountRequest = new DepositAccountRequest();
        depositAccountRequest.setBalance(BigDecimal.ONE);

        return depositAccountRequest;
    }

}
