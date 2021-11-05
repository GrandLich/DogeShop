package dev.mrlich.dogeshop.api.model.request;

import lombok.Data;

@Data
public class LoginRequest {

    private String name;
    private String password;

}
