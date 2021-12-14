package dev.mrlich.dogeshop.api;

import dev.mrlich.dogeshop.api.dto.AccountDto;
import dev.mrlich.dogeshop.api.dto.OrderDto;
import dev.mrlich.dogeshop.api.dto.request.CreateAccountRequest;
import dev.mrlich.dogeshop.api.dto.request.DepositAccountRequest;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/account")
public interface AccountResource {

    @ApiOperation("Получить аккаунт по ID")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    AccountDto getAccount(@PathVariable("id") Long accountId);

    @ApiOperation("Создать аккаунт")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    AccountDto createAccount(@RequestBody CreateAccountRequest request);

    @ApiOperation(
            value = "Пополнить баланс аккаунта",
            notes = "Для использования метода необходимо авторизоваться"
    )
    @PostMapping("/deposit")
    void depositAccount(@RequestBody DepositAccountRequest request);

    @ApiOperation("Получить список заказов аккаунта")
    @GetMapping("/{id}/orders")
    List<OrderDto> getAccountOrders(@PathVariable("id") Long accountId);

}
