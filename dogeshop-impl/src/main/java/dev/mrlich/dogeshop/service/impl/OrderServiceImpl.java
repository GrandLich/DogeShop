package dev.mrlich.dogeshop.service.impl;

import dev.mrlich.dogeshop.entity.AccountEntity;
import dev.mrlich.dogeshop.entity.OrderEntity;
import dev.mrlich.dogeshop.entity.SkinEntity;
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
    public Optional<OrderEntity> getOrder(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public List<OrderEntity> getOrders(AccountEntity account) {
        EntityGraph<?> graph = em.createEntityGraph("AccountEntity.orders");
        TypedQuery<AccountEntity> q = em.createQuery("SELECT a FROM AccountEntity a where a.id = " + account.getId(), AccountEntity.class)
                .setHint("javax.persistence.fetchgraph", graph);
        AccountEntity entity = q.getSingleResult();
        return entity.getOrders();
    }

    @Override
    public OrderEntity createOrder(AccountEntity account, SkinEntity skin) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setAccount(account);
        orderEntity.setDate(Instant.now());
        orderEntity.setSkinName(skin.getName());
        orderEntity.setPrice(skin.getPrice());
        orderRepository.save(orderEntity);
        return orderEntity;
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public void updateOrder(OrderEntity orderEntity) {
        orderRepository.save(orderEntity);
    }
}
