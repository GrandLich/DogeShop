package dev.mrlich.dogeshop.auth;

import dev.mrlich.dogeshop.entity.AccountEntity;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
@Data
public class UserAuthentication {

    private AccountEntity currentAccount;

    public boolean isLoggedIn() {
        return currentAccount != null;
    }

}
