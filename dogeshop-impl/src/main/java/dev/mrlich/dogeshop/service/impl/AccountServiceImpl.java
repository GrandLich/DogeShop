package dev.mrlich.dogeshop.service.impl;

import dev.mrlich.dogeshop.api.exception.AccountAlreadyExistsException;
import dev.mrlich.dogeshop.entity.Account;
import dev.mrlich.dogeshop.entity.Order;
import dev.mrlich.dogeshop.entity.Skin;
import dev.mrlich.dogeshop.repository.AccountRepository;
import dev.mrlich.dogeshop.repository.OrderRepository;
import dev.mrlich.dogeshop.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final OrderRepository orderRepository;

    @Override
    public Optional<Account> getAccount(Long id) {
        return accountRepository.findById(id);
    }

    @Override
    public Optional<Account> getAccount(String name) {
        return accountRepository.findByName(name);
    }

    @Override
    public Optional<Account> findByNameAndPassword(String name, String password) {
        return accountRepository.findByNameAndPassword(name, password);
    }

    @Override
    public Account createAccount(String name, String password) {
        if (accountRepository.findByName(name).isPresent()) {
            throw new AccountAlreadyExistsException(name);
        }
        Account account = new Account();
        account.setName(name);
        account.setPassword(password);
        return accountRepository.save(account);
    }

    @Override
    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }

    @Override
    public void updateAccount(Account account) {
        accountRepository.save(account);
    }

    @Override
    public void clearCart(Long accountId) {
        getAccount(accountId).ifPresent(account -> {
            account.getCartItems().clear();
            accountRepository.save(account);
        });
    }

    @Override
    public void addSkinToCart(Long accountId, Skin skin) {
        getAccount(accountId).ifPresent(account -> {
            account.getCartItems().add(skin);
            accountRepository.save(account);
        });
    }

    @Override
    public void addOrderToAccount(Long accountId, Order order) {
        getAccount(accountId).ifPresent(account -> {
            account.getOrders().add(order);
            accountRepository.save(account);
        });
    }

    @Override
    public void setBalance(Account account, BigDecimal balance) {
        account.setBalance(balance);
        accountRepository.save(account);
    }

    @Override
    public Set<Skin> getSkinsInCart(Long accountId) {
        Set<Skin> skins = new HashSet<>();
        getAccount(accountId).ifPresent(account -> skins.addAll(account.getCartItems()));
        return skins;
    }

    @Override
    public List<Order> getOrders(Long accountId, Pageable pageable) {
        return orderRepository.findAllByAccountId(accountId, pageable);
    }

}
