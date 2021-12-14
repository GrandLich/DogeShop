package dev.mrlich.dogeshop.service.impl;

import dev.mrlich.dogeshop.api.exception.AccountAlreadyExistsException;
import dev.mrlich.dogeshop.entity.Account;
import dev.mrlich.dogeshop.entity.Order;
import dev.mrlich.dogeshop.entity.Skin;
import dev.mrlich.dogeshop.repository.AccountRepository;
import dev.mrlich.dogeshop.util.test.TestUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountServiceImpl service;

    @Test
    void getAccountMustCallFindByIdMethod() {
        Long id = TestUtils.randomLong();

        service.getAccount(id);

        verify(accountRepository).findById(id);
    }

    @Test
    void getAccountMustCallFindByNameMethod() {
        String randomName = UUID.randomUUID().toString();

        service.getAccount(randomName);

        verify(accountRepository).findByName(randomName);
    }

    @Test
    void findByNameAndPasswordMustCallRepositoryMethod() {
        String name = UUID.randomUUID().toString();
        String password = UUID.randomUUID().toString();

        service.findByNameAndPassword(name, password);

        verify(accountRepository).findByNameAndPassword(name, password);
    }

    @Test
    void positiveCreateAccountMustReturnCorrectAccount() {
        String name = UUID.randomUUID().toString();
        String password = UUID.randomUUID().toString();

        Account expected = new Account();
        expected.setName(name);
        expected.setPassword(password);

        when(accountRepository.findByName(anyString())).thenReturn(Optional.empty());
        when(accountRepository.save(any())).thenReturn(expected);

        Account actual = service.createAccount(name, password);

        assertNotNull(actual);
        assertEquals(actual, expected);
    }

    @Test
    void positiveCreateAccountMustCallSaveMethod() {
        String name = UUID.randomUUID().toString();
        String password = UUID.randomUUID().toString();

        when(accountRepository.findByName(anyString())).thenReturn(Optional.empty());

        service.createAccount(name, password);

        verify(accountRepository).save(any());
    }

    @Test
    void negativeCreateAccountMustThrowAccountAlreadyExistsException() {
        String name = UUID.randomUUID().toString();
        String password = UUID.randomUUID().toString();

        when(accountRepository.findByName(anyString())).thenReturn(Optional.of(new Account()));

        assertThrows(AccountAlreadyExistsException.class,
                () -> service.createAccount(name, password));
    }

    @Test
    void deleteAccountMustCallRepositoryMethod() {
        Long id = TestUtils.randomLong();

        service.deleteAccount(id);

        verify(accountRepository).deleteById(id);
    }

    @Test
    void updateAccountMustCallSaveMethod() {
        Account account = new Account();

        service.updateAccount(account);

        verify(accountRepository).save(account);
    }

    @Test
    void clearCartMustCallSaveMethodAndClearCartItems() {
        Account account = generateAccount();

        when(accountRepository.findById(account.getId())).thenReturn(Optional.of(account));

        service.clearCart(account.getId());

        assertEquals(0, account.getCartItems().size());
        verify(accountRepository).save(account);
    }

    @Test
    void addSkinToCartMustCallSaveMethodAndAddSkinToCartItems() {
        Account account = generateAccount();
        Skin skin = new Skin();

        when(accountRepository.findById(account.getId())).thenReturn(Optional.of(account));

        service.addSkinToCart(account.getId(), skin);

        assertEquals(3, account.getCartItems().size());
        verify(accountRepository).save(account);
    }

    @Test
    void addOrderMustCallSaveMethodAndAddOrder() {
        Account account = new Account();
        Order order = new Order();

        when(accountRepository.findById(account.getId())).thenReturn(Optional.of(account));

        service.addOrderToAccount(account.getId(), order);

        assertEquals(1, account.getOrders().size());
        verify(accountRepository).save(account);
    }

    @Test
    void setBalanceMustCallSaveMethodAndSetBalance() {
        Account account = new Account();
        BigDecimal bigDecimal = BigDecimal.ONE;

        service.setBalance(account, bigDecimal);

        assertEquals(BigDecimal.ONE, account.getBalance());
        verify(accountRepository).save(account);
    }

    private Account generateAccount() {
        Account account = new Account();
        account.setId(1L);
        account.setCartItems(generateCart());

        return account;
    }

    private HashSet<Skin> generateCart() {
        HashSet<Skin> skins = new HashSet<>();
        skins.add(createSkin("Vasya"));
        skins.add(createSkin("Pupkin"));
        return skins;
    }

    private Skin createSkin(String name) {
        Skin skin = new Skin();
        skin.setName(name);
        return skin;
    }

}
