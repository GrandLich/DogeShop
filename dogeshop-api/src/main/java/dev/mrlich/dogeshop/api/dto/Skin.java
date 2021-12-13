package dev.mrlich.dogeshop.api.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Skin {

    private Long id;
    private String name;
    private String pictureUrl;
    private String description;
    private BigDecimal price;

}
