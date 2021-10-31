package dev.mrlich.dogeshop.controller.api;

import dev.mrlich.dogeshop.api.AccountApi;
import dev.mrlich.dogeshop.api.exception.AccountAlreadyExistsException;
import dev.mrlich.dogeshop.api.model.Account;
import dev.mrlich.dogeshop.api.model.request.CreateAccountRequest;
import dev.mrlich.dogeshop.entity.AccountEntity;
import dev.mrlich.dogeshop.service.AccountService;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class AccountApiImpl implements AccountApi {

    private final MapperFacade mapper;
    private final AccountService accountService;

    @Override
    public ResponseEntity<Account> getAccount(Long accountId) {
        Optional<AccountEntity> accountEntity = accountService.getAccount(accountId);
        if(accountEntity.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Account account = mapper.map(accountEntity.get(), Account.class);
        return ResponseEntity.ok(account);
    }

    @Override
    public ResponseEntity<Account> createAccount(CreateAccountRequest request) {
        AccountEntity account = accountService.createAccount(request.getName(), request.getPassword());
        return ResponseEntity.status(201).body(mapper.map(account, Account.class));
    }

    @ExceptionHandler(AccountAlreadyExistsException.class)
    public ResponseEntity<Account> handleAccountAlreadyExistsException() {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}
