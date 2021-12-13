package dev.mrlich.dogeshop.api;

import dev.mrlich.dogeshop.api.dto.AccountDto;
import dev.mrlich.dogeshop.api.dto.OrderDto;
import dev.mrlich.dogeshop.api.dto.request.CreateAccountRequest;
import dev.mrlich.dogeshop.api.dto.request.DepositAccountRequest;
import dev.mrlich.dogeshop.api.dto.response.LogoutResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value =  "account")
@RestController
@RequestMapping("/api/account")
public interface AccountApi {

    @ApiOperation(
            value = "Получить аккаунт по ID",
            nickname = "getAccount",
            response = AccountDto.class,
            tags = "getAccount"
    )
    @ApiResponses({
            @ApiResponse(
                    code = 200,
                    message = "OK",
                    response = AccountDto.class
            ),
            @ApiResponse(
                    code = 404,
                    message = "Not Found"
            )
    })
    @GetMapping("/{id}")
    ResponseEntity<AccountDto> getAccount(@PathVariable("id") Long accountId);

    @ApiOperation(
            value = "Создать аккаунт",
            nickname = "createAccount",
            response = AccountDto.class,
            tags = "createAccount"
    )
    @ApiResponses({
            @ApiResponse(
                    code = 201,
                    message = "Created",
                    response = AccountDto.class
            ),
            @ApiResponse(
                    code = 404,
                    message = "Not Found"
            )
    })
    @PostMapping("/create")
    ResponseEntity<AccountDto> createAccount(@RequestBody CreateAccountRequest request);

    @ApiOperation(
            value = "Пополнить баланс аккаунта",
            nickname = "depositAccount",
            response = AccountDto.class,
            notes = "Для использования метода необходимо авторизоваться",
            tags = "depositAccount"
    )
    @ApiResponses({
            @ApiResponse(
                    code = 200,
                    message = "OK"
            ),
            @ApiResponse(
                    code = 403,
                    message = "Forbidden"
            )
    })
    @PostMapping("/deposit")
    ResponseEntity<Void> depositAccount(@RequestBody DepositAccountRequest request);

    @ApiOperation(
            value = "Получить список заказов аккаунта",
            nickname = "getAccountOrders",
            response = List.class,
            tags = "getAccountOrders"
    )
    @ApiResponses({
            @ApiResponse(
                    code = 200,
                    message = "OK",
                    response = List.class
            ),
            @ApiResponse(
                    code = 404,
                    message = "Not Found"
            )
    })
    @GetMapping("/{id}/orders")
    ResponseEntity<List<OrderDto>> getAccountOrders(@PathVariable("id") Long accountId);

}
