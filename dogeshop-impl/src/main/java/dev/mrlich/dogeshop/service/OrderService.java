package dev.mrlich.dogeshop.service;

import dev.mrlich.dogeshop.entity.AccountEntity;
import dev.mrlich.dogeshop.entity.OrderEntity;
import dev.mrlich.dogeshop.entity.SkinEntity;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    Optional<OrderEntity> getOrder(Long id);

    List<OrderEntity> getOrders(AccountEntity account);

    OrderEntity createOrder(AccountEntity account, SkinEntity skin);

    void deleteOrder(Long id);

    void updateOrder(OrderEntity orderEntity);

}
