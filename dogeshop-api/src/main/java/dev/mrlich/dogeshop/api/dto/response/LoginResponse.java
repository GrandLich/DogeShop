package dev.mrlich.dogeshop.api.dto.response;

import lombok.Data;

@Data
public class LoginResponse {

    private String accountName;
    private String message;

    public LoginResponse accountName(String accountName) {
        this.accountName = accountName;
        return this;
    }

    public LoginResponse message(String message) {
        this.message = message;
        return this;
    }

}
