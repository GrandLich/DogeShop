package dev.mrlich.dogeshop.repository;

import dev.mrlich.dogeshop.entity.SkinEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SkinRepository extends CrudRepository<SkinEntity, Long> {

    @Query("select skin from SkinEntity skin where skin.name = ?")
    Optional<SkinEntity> findByName(String name);

}
