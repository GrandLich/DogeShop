package dev.mrlich.dogeshop.api;

import dev.mrlich.dogeshop.api.dto.Account;
import dev.mrlich.dogeshop.api.dto.Order;
import dev.mrlich.dogeshop.api.dto.request.CreateAccountRequest;
import dev.mrlich.dogeshop.api.dto.request.DepositAccountRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/account")
public interface AccountApi {

    @GetMapping("/{id}")
    ResponseEntity<Account> getAccount(@PathVariable("id") Long accountId);

    @PostMapping("/create")
    ResponseEntity<Account> createAccount(@RequestBody CreateAccountRequest request);

    @PostMapping("/deposit")
    ResponseEntity<Void> depositAccount(@RequestBody DepositAccountRequest request);

    @GetMapping("/{id}/orders")
    ResponseEntity<List<Order>> getAccountOrders(@PathVariable("id") Long accountId);

}
