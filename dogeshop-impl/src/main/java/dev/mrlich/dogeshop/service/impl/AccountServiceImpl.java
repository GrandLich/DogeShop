package dev.mrlich.dogeshop.service.impl;

import dev.mrlich.dogeshop.api.exception.AccountAlreadyExistsException;
import dev.mrlich.dogeshop.entity.AccountEntity;
import dev.mrlich.dogeshop.repository.AccountRepository;
import dev.mrlich.dogeshop.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public Optional<AccountEntity> getAccount(Long id) {
        return accountRepository.findById(id);
    }

    @Override
    public Optional<AccountEntity> getAccount(String name) {
        return accountRepository.findByName(name);
    }

    @Override
    public AccountEntity createAccount(String name, String password) {
        if(accountRepository.findByName(name).isPresent()) {
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

}
