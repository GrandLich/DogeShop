package dev.mrlich.dogeshop.service.impl;

import dev.mrlich.dogeshop.api.exception.AccountAlreadyExistsException;
import dev.mrlich.dogeshop.entity.AccountEntity;
import dev.mrlich.dogeshop.entity.OrderEntity;
import dev.mrlich.dogeshop.entity.SkinEntity;
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
    public Optional<AccountEntity> getAccount(Long id) {
        return accountRepository.findById(id);
    }

    @Override
    public Optional<AccountEntity> getAccount(String name) {
        return accountRepository.findByName(name);
    }

    @Override
    public Optional<AccountEntity> findByNameAndPassword(String name, String password) {
        return accountRepository.findByNameAndPassword(name, password);
    }

    @Override
    public AccountEntity createAccount(String name, String password) {
        if (accountRepository.findByName(name).isPresent()) {
            throw new AccountAlreadyExistsException(name);
        }
        AccountEntity account = new AccountEntity();
        account.setName(name);
        account.setPassword(password);
        return accountRepository.save(account);
    }

    @Override
    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }

    @Override
    public void updateAccount(AccountEntity account) {
        accountRepository.save(account);
    }

    @Override
    public void clearCart(AccountEntity account) {
        EntityGraph<?> graph = em.createEntityGraph("AccountEntity.cartItems");
        TypedQuery<AccountEntity> q = em.createQuery("SELECT a FROM AccountEntity a where a.id = " + account.getId(), AccountEntity.class)
                .setHint("javax.persistence.fetchgraph", graph);
        AccountEntity entity = q.getSingleResult();
        entity.getCartItems().clear();
        accountRepository.save(account);
    }

    @Override
    public void addSkinToCart(AccountEntity account, SkinEntity skin) {
        EntityGraph<?> graph = em.createEntityGraph("AccountEntity.cartItems");
        TypedQuery<AccountEntity> q = em.createQuery("SELECT a FROM AccountEntity a where a.id = " + account.getId(), AccountEntity.class)
                .setHint("javax.persistence.fetchgraph", graph);
        AccountEntity entity = q.getSingleResult();
        entity.getCartItems().add(skin);
        accountRepository.save(account);
    }

    @Override
    public void addOrderToAccount(AccountEntity account, OrderEntity order) {
        EntityGraph<?> graph = em.createEntityGraph("AccountEntity.orders");
        TypedQuery<AccountEntity> q = em.createQuery("SELECT a FROM AccountEntity a where a.id = " + account.getId(), AccountEntity.class)
                .setHint("javax.persistence.fetchgraph", graph);
        AccountEntity entity = q.getSingleResult();
        entity.getOrders().add(order);
        accountRepository.save(account);
    }

    @Override
    public void setBalance(AccountEntity account, BigDecimal balance) {
        account.setBalance(balance);
        accountRepository.save(account);
    }

    @Override
    public Set<SkinEntity> getSkinsInCart(AccountEntity account) {
        EntityGraph<?> graph = em.createEntityGraph("AccountEntity.cartItems");
        TypedQuery<AccountEntity> q = em.createQuery("SELECT a FROM AccountEntity a where a.id = " + account.getId(), AccountEntity.class)
                .setHint("javax.persistence.fetchgraph", graph);
        AccountEntity entity = q.getSingleResult();
        return entity.getCartItems();
    }

}
