package dev.mrlich.dogeshop.service;

import dev.mrlich.dogeshop.entity.Account;
import dev.mrlich.dogeshop.entity.Order;
import dev.mrlich.dogeshop.entity.Skin;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    Optional<Order> getOrder(Long id);

    List<Order> getOrders(Account account);

    Order createOrder(Account account, Skin skin);

    void deleteOrder(Long id);

    void updateOrder(Order order);

}
