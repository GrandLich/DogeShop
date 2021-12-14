package dev.mrlich.dogeshop.auth;

import dev.mrlich.dogeshop.entity.Account;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.concurrent.atomic.AtomicReference;

@Component
@SessionScope
public class UserAuthentication {

    private final AtomicReference<Account> currentAccount = new AtomicReference<>();

    public boolean isLoggedIn() {
        return currentAccount.get() != null;
    }

    public Account getAccount() {
        return currentAccount.get();
    }

    public void switchAccount(Account account) {
        currentAccount.set(account);
    }

}
