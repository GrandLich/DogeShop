package dev.mrlich.dogeshop.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "account")
@NamedEntityGraphs({
        @NamedEntityGraph(name = "AccountEntity.cartItems",
                attributeNodes = @NamedAttributeNode("cartItems")),
        @NamedEntityGraph(name = "AccountEntity.orders",
                attributeNodes = @NamedAttributeNode("orders"))
})
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_seq")
    @SequenceGenerator(name = "account_seq", sequenceName = "account_pk_seq", allocationSize = 1)
    private Long id;
    private String name;
    private String password;
    private BigDecimal balance = new BigDecimal(0);

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "account_skin_cart",
            joinColumns = @JoinColumn(name = "accountId"),
            inverseJoinColumns = @JoinColumn(name = "skinId"))
    private Set<SkinEntity> cartItems = new HashSet<>();

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<OrderEntity> orders = new ArrayList<>();

    @Override
    public String toString() {
        return id + ":" + name;
    }

}
