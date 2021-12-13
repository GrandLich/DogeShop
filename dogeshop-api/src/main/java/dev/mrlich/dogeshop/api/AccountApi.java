package dev.mrlich.dogeshop.api;

import dev.mrlich.dogeshop.api.dto.AccountDto;
import dev.mrlich.dogeshop.api.dto.OrderDto;
import dev.mrlich.dogeshop.api.dto.request.CreateAccountRequest;
import dev.mrlich.dogeshop.api.dto.request.DepositAccountRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/account")
public interface AccountApi {

    @GetMapping("/{id}")
    ResponseEntity<AccountDto> getAccount(@PathVariable("id") Long accountId);

    @PostMapping("/create")
    ResponseEntity<AccountDto> createAccount(@RequestBody CreateAccountRequest request);

    @PostMapping("/deposit")
    ResponseEntity<Void> depositAccount(@RequestBody DepositAccountRequest request);

    @GetMapping("/{id}/orders")
    ResponseEntity<List<OrderDto>> getAccountOrders(@PathVariable("id") Long accountId);

}
