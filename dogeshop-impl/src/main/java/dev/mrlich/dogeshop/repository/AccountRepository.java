package dev.mrlich.dogeshop.repository;

import dev.mrlich.dogeshop.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("SELECT account FROM Account account WHERE account.name = :name")
    Optional<Account> findByName(String name);

    @Query("SELECT account FROM Account account WHERE account.name = :name AND account.password = :password")
    Optional<Account> findByNameAndPassword(String name, String password);

}
