package dev.mrlich.dogeshop.api.model.request;

import lombok.Data;

@Data
public class CreateAccountRequest {

    private String name;
    private String password;

}
