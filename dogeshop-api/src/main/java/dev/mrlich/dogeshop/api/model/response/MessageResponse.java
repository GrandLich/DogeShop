package dev.mrlich.dogeshop.api.model.response;

import lombok.Data;

@Data
public class MessageResponse {

    private String message;

    public MessageResponse message(String message) {
        this.message = message;
        return this;
    }

}
