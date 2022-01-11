package com.example.shop.repository;

import com.example.shop.domain.Order;
import com.example.shop.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query(value = "SELECT * FROM orders o WHERE o.fk_user = :userId", nativeQuery = true)
    List<Order> findAllOrdersByUserId(@Param("userId") Long userId);
}