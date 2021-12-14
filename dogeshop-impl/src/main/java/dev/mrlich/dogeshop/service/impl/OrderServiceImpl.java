package dev.mrlich.dogeshop.service.impl;

import dev.mrlich.dogeshop.entity.Account;
import dev.mrlich.dogeshop.entity.Order;
import dev.mrlich.dogeshop.entity.Skin;
import dev.mrlich.dogeshop.repository.OrderRepository;
import dev.mrlich.dogeshop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final EntityManager em;

    @Override
    public Optional<Order> getOrder(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public Order createOrder(Account account, Skin skin) {
        Order order = new Order();
        order.setAccount(account);
        order.setDate(Instant.now());
        order.setSkinName(skin.getName());
        order.setPrice(skin.getPrice());
        orderRepository.save(order);
        return order;
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public void updateOrder(Order order) {
        orderRepository.save(order);
    }
}
