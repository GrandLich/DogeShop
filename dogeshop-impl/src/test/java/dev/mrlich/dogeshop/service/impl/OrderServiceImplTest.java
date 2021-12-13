package dev.mrlich.dogeshop.service.impl;

import dev.mrlich.dogeshop.entity.Account;
import dev.mrlich.dogeshop.entity.Order;
import dev.mrlich.dogeshop.entity.Skin;
import dev.mrlich.dogeshop.repository.OrderRepository;
import dev.mrlich.dogeshop.util.test.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl service;

    @Test
    void getOrderMustCallFindByIdMethod() {
        Long id = TestUtils.randomLong();

        service.getOrder(id);

        verify(orderRepository).findById(id);
    }

    @Test
    void createOrderMustReturnCorrectOrder() {
        Account account = generateAccount();
        Skin skin = generateSkin();

        Order actual = service.createOrder(account, skin);

        assertNotNull(actual);
        assertEquals(actual.getAccount(), account);
        assertEquals(actual.getSkinName(), skin.getName());
        assertEquals(actual.getPrice(), skin.getPrice());
        assertNotNull(actual.getDate());
    }

    @Test
    void createOrderMustCallSaveMethod() {
        Account account = generateAccount();
        Skin skin = generateSkin();

        service.createOrder(account, skin);

        verify(orderRepository).save(any());
    }

    @Test
    void deleteOrderMustCallRepositoryMethod() {
        Long id = TestUtils.randomLong();

        service.deleteOrder(id);

        verify(orderRepository).deleteById(id);
    }

    @Test
    void updateOrderMustCallSaveMethod() {
        Order order = new Order();

        service.updateOrder(order);

        verify(orderRepository).save(order);
    }

    private Account generateAccount() {
        Account account = new Account();
        account.setId(TestUtils.randomLong());
        account.setName(UUID.randomUUID().toString());
        account.setPassword(UUID.randomUUID().toString());
        account.setBalance(BigDecimal.ZERO);

        return account;
    }

    private Skin generateSkin() {
        Skin skin = new Skin();
        skin.setId(TestUtils.randomLong());
        skin.setName(UUID.randomUUID().toString());
        skin.setPrice(BigDecimal.ONE);

        return skin;
    }

}
