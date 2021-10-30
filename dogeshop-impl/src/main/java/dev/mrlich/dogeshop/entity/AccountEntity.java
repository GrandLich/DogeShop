package dev.mrlich.dogeshop.entity;

import dev.mrlich.dogeshop.api.model.Cart;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "account")
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String password;
    private BigDecimal balance;
    @Embedded
    private Cart cart;

}
