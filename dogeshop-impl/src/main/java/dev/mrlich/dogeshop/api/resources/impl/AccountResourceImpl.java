package dev.mrlich.dogeshop.api.resources.impl;

import dev.mrlich.dogeshop.api.resource.AccountResource;
import dev.mrlich.dogeshop.api.dto.AccountDto;
import dev.mrlich.dogeshop.api.exception.AccountAlreadyExistsException;
import dev.mrlich.dogeshop.api.dto.OrderDto;
import dev.mrlich.dogeshop.api.dto.request.CreateAccountRequest;
import dev.mrlich.dogeshop.api.dto.request.DepositAccountRequest;
import dev.mrlich.dogeshop.api.exception.EntityNotFoundException;
import dev.mrlich.dogeshop.api.exception.ActionIsNotAllowedException;
import dev.mrlich.dogeshop.auth.UserAuthentication;
import dev.mrlich.dogeshop.entity.Account;
import dev.mrlich.dogeshop.entity.Order;
import dev.mrlich.dogeshop.service.AccountService;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class AccountResourceImpl implements AccountResource {

    private final MapperFacade mapper;
    private final AccountService accountService;
    private final UserAuthentication authentication;

    @Override
    public AccountDto getAccount(Long accountId) {
        Optional<Account> accountEntity = accountService.getAccount(accountId);
        if(accountEntity.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return mapper.map(accountEntity.get(), AccountDto.class);
    }

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    public AccountDto createAccount(CreateAccountRequest request) {
        Account account = accountService.createAccount(request.getName(), request.getPassword());
        return mapper.map(account, AccountDto.class);
    }

    @Override
    public void depositAccount(DepositAccountRequest request) {
        if(!authentication.isLoggedIn()) {
            throw new ActionIsNotAllowedException();
        }
        BigDecimal newBalance = authentication.getAccount().getBalance().add(request.getBalance());
        accountService.setBalance(authentication.getAccount(), newBalance);
    }

    @Override
    public List<OrderDto> getAccountOrders(Long accountId) {
        Optional<Account> accountEntity = accountService.getAccount(accountId);
        if(accountEntity.isEmpty()) {
            throw new EntityNotFoundException();
        }
        List<Order> orders = accountService.getOrders(accountEntity.get().getId(), Pageable.unpaged());
        return mapper.mapAsList(orders, OrderDto.class);
    }

    @ExceptionHandler(AccountAlreadyExistsException.class)
    public ResponseEntity<AccountDto> handleAccountAlreadyExistsException() {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}
