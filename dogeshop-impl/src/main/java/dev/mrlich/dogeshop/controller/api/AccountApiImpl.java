package dev.mrlich.dogeshop.controller.api;

import dev.mrlich.dogeshop.api.AccountApi;
import dev.mrlich.dogeshop.api.dto.AccountDto;
import dev.mrlich.dogeshop.api.exception.AccountAlreadyExistsException;
import dev.mrlich.dogeshop.api.dto.OrderDto;
import dev.mrlich.dogeshop.api.dto.request.CreateAccountRequest;
import dev.mrlich.dogeshop.api.dto.request.DepositAccountRequest;
import dev.mrlich.dogeshop.auth.UserAuthentication;
import dev.mrlich.dogeshop.entity.Account;
import dev.mrlich.dogeshop.entity.Order;
import dev.mrlich.dogeshop.service.AccountService;
import dev.mrlich.dogeshop.service.OrderService;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class AccountApiImpl implements AccountApi {

    private final MapperFacade mapper;
    private final AccountService accountService;
    private final OrderService orderService;
    private final UserAuthentication authentication;

    @Override
    public ResponseEntity<AccountDto> getAccount(Long accountId) {
        Optional<Account> accountEntity = accountService.getAccount(accountId);
        if(accountEntity.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        AccountDto accountDto = mapper.map(accountEntity.get(), AccountDto.class);
        return ResponseEntity.ok(accountDto);
    }

    @Override
    public ResponseEntity<AccountDto> createAccount(CreateAccountRequest request) {
        Account account = accountService.createAccount(request.getName(), request.getPassword());
        return ResponseEntity.status(201).body(mapper.map(account, AccountDto.class));
    }

    @Override
    public ResponseEntity<Void> depositAccount(DepositAccountRequest request) {
        if(!authentication.isLoggedIn()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        BigDecimal newBalance = authentication.getCurrentAccount().getBalance().add(request.getBalance());
        accountService.setBalance(authentication.getCurrentAccount(), newBalance);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<OrderDto>> getAccountOrders(Long accountId) {
        Optional<Account> accountEntity = accountService.getAccount(accountId);
        if(accountEntity.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<Order> orders = orderService.getOrders(accountEntity.get());
        return ResponseEntity.ok(mapper.mapAsList(orders, OrderDto.class));
    }

    @ExceptionHandler(AccountAlreadyExistsException.class)
    public ResponseEntity<AccountDto> handleAccountAlreadyExistsException() {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}
