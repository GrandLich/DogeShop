package dev.mrlich.dogeshop.repository;

import dev.mrlich.dogeshop.entity.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByAccountId(Long accountId, Pageable pageable);

}
