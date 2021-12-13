package dev.mrlich.dogeshop.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Data
@Entity
@Table(name = "orderhistory")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orderhistory_seq")
    @SequenceGenerator(name="orderhistory_seq", sequenceName="orderhistory_pk_seq", allocationSize = 1)
    private Long id;
    private String skinName;
    private Instant date;
    private BigDecimal price;
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

}
