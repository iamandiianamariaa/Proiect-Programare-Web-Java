package com.example.shop.repository;

import com.example.shop.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Modifying
    @Query(value = "UPDATE products p " +
            "SET p.review_score = (SELECT AVG(r.rating) FROM reviews r WHERE r.fk_product = :productId)" +
            "WHERE p.id = :productId", nativeQuery = true)
    void setAverageScore(@Param("productId") Long productId);

    Optional<Product> findProductByName(@Param("name") String name);

    List<Product> findProductsByBrand(@Param("brand") String brand);

    @Query(value = "SELECT * FROM products p JOIN product_categories pc ON p.id = pc.product_id JOIN categories c ON pc.category_id = c.id " +
            "WHERE c.name = :category", nativeQuery = true)
    List<Product> findProductsByCategory(@Param("category") String name);

    @Query(value = "SELECT * FROM products p JOIN product_categories pc ON p.id = pc.product_id JOIN categories c ON pc.category_id = c.id " +
            "WHERE c.id = :id", nativeQuery = true)
    List<Product> findProductsByCategoryId(@Param("id") Long id);

    @Query(value = "SELECT * FROM products p WHERE p.price BETWEEN :lower AND :upper", nativeQuery = true)
    List<Product> findProductsByPriceBetween(@Param("lower") Double lower, @Param("upper") Double upper);
}
