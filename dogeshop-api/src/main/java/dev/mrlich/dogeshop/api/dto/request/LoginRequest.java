package dev.mrlich.dogeshop.api.dto.request;

import lombok.Data;

@Data
public class LoginRequest {

    private String name;
    private String password;

}
