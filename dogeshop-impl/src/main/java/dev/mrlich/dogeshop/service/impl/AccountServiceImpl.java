package dev.mrlich.dogeshop.service.impl;

import dev.mrlich.dogeshop.api.exception.AccountAlreadyExistsException;
import dev.mrlich.dogeshop.entity.Account;
import dev.mrlich.dogeshop.entity.Order;
import dev.mrlich.dogeshop.entity.Skin;
import dev.mrlich.dogeshop.repository.AccountRepository;
import dev.mrlich.dogeshop.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final EntityManager em;

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
    public void clearCart(Account account) {
        EntityGraph<?> graph = em.createEntityGraph("AccountEntity.cartItems");
        TypedQuery<Account> q = em.createQuery("SELECT a FROM Account a where a.id = " + account.getId(), Account.class)
                .setHint("javax.persistence.fetchgraph", graph);
        Account entity = q.getSingleResult();
        entity.getCartItems().clear();
        accountRepository.save(account);
    }

    @Override
    public void addSkinToCart(Account account, Skin skin) {
        EntityGraph<?> graph = em.createEntityGraph("AccountEntity.cartItems");
        TypedQuery<Account> q = em.createQuery("SELECT a FROM Account a where a.id = " + account.getId(), Account.class)
                .setHint("javax.persistence.fetchgraph", graph);
        Account entity = q.getSingleResult();
        entity.getCartItems().add(skin);
        accountRepository.save(account);
    }

    @Override
    public void addOrderToAccount(Account account, Order order) {
        EntityGraph<?> graph = em.createEntityGraph("AccountEntity.orders");
        TypedQuery<Account> q = em.createQuery("SELECT a FROM Account a where a.id = " + account.getId(), Account.class)
                .setHint("javax.persistence.fetchgraph", graph);
        Account entity = q.getSingleResult();
        entity.getOrders().add(order);
        accountRepository.save(account);
    }

    @Override
    public void setBalance(Account account, BigDecimal balance) {
        account.setBalance(balance);
        accountRepository.save(account);
    }

    @Override
    public Set<Skin> getSkinsInCart(Account account) {
        EntityGraph<?> graph = em.createEntityGraph("AccountEntity.cartItems");
        TypedQuery<Account> q = em.createQuery("SELECT a FROM Account a where a.id = " + account.getId(), Account.class)
                .setHint("javax.persistence.fetchgraph", graph);
        Account entity = q.getSingleResult();
        return entity.getCartItems();
    }

}
