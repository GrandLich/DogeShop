package dev.mrlich.dogeshop.auth;

import dev.mrlich.dogeshop.entity.Account;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
@Data
public class UserAuthentication {

    private Account currentAccount;

    public boolean isLoggedIn() {
        return currentAccount != null;
    }

}
