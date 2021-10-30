package dev.mrlich.dogeshop.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

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

    @ManyToMany
    @JoinTable(name = "accountSkinCart",
    joinColumns = @JoinColumn(name = "accountId"),
    inverseJoinColumns = @JoinColumn(name = "skinId"))
    private Set<SkinEntity> cartItems;

}
