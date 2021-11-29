package dev.mrlich.dogeshop.service;

import dev.mrlich.dogeshop.entity.AccountEntity;
import dev.mrlich.dogeshop.entity.SkinEntity;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

public interface AccountService {

    Optional<AccountEntity> getAccount(Long id);

    Optional<AccountEntity> getAccount(String name);

    Optional<AccountEntity> findByNameAndPassword(String name, String password);

    AccountEntity createAccount(String name, String password);

    void deleteAccount(Long id);

    void updateAccount(AccountEntity account);

    void clearCart(AccountEntity account);

    void addSkinToCart(AccountEntity account, SkinEntity skin);

    void setBalance(AccountEntity account, BigDecimal balance);

    Set<SkinEntity> getSkinsInCart(AccountEntity account);

}
