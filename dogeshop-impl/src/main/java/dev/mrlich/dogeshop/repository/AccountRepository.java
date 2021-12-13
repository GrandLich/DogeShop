package dev.mrlich.dogeshop.repository;

import dev.mrlich.dogeshop.entity.Account;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {

    @Query("select account from AccountEntity account where account.name = :name")
    Optional<Account> findByName(String name);

    @Query("select account from AccountEntity account where account.name = :name and account.password = :password")
    Optional<Account> findByNameAndPassword(String name, String password);

}
