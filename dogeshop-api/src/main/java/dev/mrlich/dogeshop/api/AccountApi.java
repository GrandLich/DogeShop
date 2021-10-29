package dev.mrlich.dogeshop.api;

import dev.mrlich.dogeshop.api.model.Account;
import dev.mrlich.dogeshop.api.model.request.CreateAccountRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
public interface AccountApi {

    @GetMapping("/{id}")
    ResponseEntity<Account> getAccount(@RequestParam("id") Long accountId);

    @PostMapping("/create")
    ResponseEntity<Account> createAccount(@RequestBody CreateAccountRequest request);

}
