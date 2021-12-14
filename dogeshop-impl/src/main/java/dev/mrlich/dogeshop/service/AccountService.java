package dev.mrlich.dogeshop.service;

import dev.mrlich.dogeshop.entity.Account;
import dev.mrlich.dogeshop.entity.Order;
import dev.mrlich.dogeshop.entity.Skin;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface AccountService {

    Optional<Account> getAccount(Long id);

    Optional<Account> getAccount(String name);

    Optional<Account> findByNameAndPassword(String name, String password);

    Account createAccount(String name, String password);

    void deleteAccount(Long id);

    void updateAccount(Account account);

    void clearCart(Long accountId);

    void addSkinToCart(Long accountId, Skin skin);

    void addOrderToAccount(Long accountId, Order order);

    void setBalance(Account account, BigDecimal balance);

    Set<Skin> getSkinsInCart(Long accountId);

    List<Order> getOrders(Long accountId);

}
