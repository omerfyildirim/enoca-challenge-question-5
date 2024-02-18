package com.shpping.shopping.app.repository;

import com.shpping.shopping.app.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByCode(String code);

    List<Order> findAllByCustomerId(Long customerId);
}
