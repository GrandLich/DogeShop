package dev.mrlich.dogeshop.repository;

import dev.mrlich.dogeshop.entity.AccountEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends CrudRepository<AccountEntity, Long> {

    @Query("select account from AccountEntity account where account.name = ?1")
    Optional<AccountEntity> findByName(String name);

}
