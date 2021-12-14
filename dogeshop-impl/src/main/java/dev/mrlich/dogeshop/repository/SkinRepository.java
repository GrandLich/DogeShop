package dev.mrlich.dogeshop.repository;

import dev.mrlich.dogeshop.entity.Skin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SkinRepository extends JpaRepository<Skin, Long> {

    @Query("SELECT skin FROM Skin skin WHERE skin.name = ?1")
    Optional<Skin> findByName(String name);

}
