package dev.mrlich.dogeshop.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "skin")
public class SkinEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String pictureUrl;
    private String description;
    private BigDecimal price;

}
