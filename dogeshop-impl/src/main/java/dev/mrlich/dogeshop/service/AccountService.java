package dev.mrlich.dogeshop.service;

import dev.mrlich.dogeshop.entity.AccountEntity;

import javax.transaction.Transactional;
import java.util.Optional;

public interface AccountService {

    Optional<AccountEntity> getAccount(Long id);

    Optional<AccountEntity> getAccount(String name);

    @Transactional
    AccountEntity createAccount(String name, String password);

    @Transactional
    void deleteAccount(Long id);

    @Transactional
    void updateAccount(AccountEntity account);

}
