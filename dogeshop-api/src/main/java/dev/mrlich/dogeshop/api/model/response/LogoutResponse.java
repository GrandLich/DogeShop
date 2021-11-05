package dev.mrlich.dogeshop.api.model.response;

import lombok.Data;

@Data
public class LogoutResponse {

    private String message;

    public LogoutResponse message(String message) {
        this.message = message;
        return this;
    }

}
