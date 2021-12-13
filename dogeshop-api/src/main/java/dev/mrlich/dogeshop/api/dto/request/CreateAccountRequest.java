package dev.mrlich.dogeshop.api.dto.request;

import lombok.Data;

@Data
public class CreateAccountRequest {

    private String name;
    private String password;

}
