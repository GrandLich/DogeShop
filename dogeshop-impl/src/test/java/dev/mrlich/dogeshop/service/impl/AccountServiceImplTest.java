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
    @Disabled
    void clearCartMustCallSaveMethod() {
        Account account = new Account();

        service.clearCart(account);

        verify(accountRepository).save(account);
    }

    @Test
    @Disabled
    void addSkinToCartMustCallSaveMethod() {
        Account account = new Account();
        Skin skin = new Skin();

        service.addSkinToCart(account, skin);

        verify(accountRepository).save(account);
    }

    @Test
    @Disabled
    void addOrderMustCallSaveMethod() {
        Account account = new Account();
        Order order = new Order();

        service.addOrderToAccount(account, order);

        verify(accountRepository).save(account);
    }

    @Test
    void setBalanceMustCallSaveMethod() {
        Account account = new Account();
        BigDecimal bigDecimal = BigDecimal.ONE;

        service.setBalance(account, bigDecimal);

        verify(accountRepository).save(account);
    }

}
