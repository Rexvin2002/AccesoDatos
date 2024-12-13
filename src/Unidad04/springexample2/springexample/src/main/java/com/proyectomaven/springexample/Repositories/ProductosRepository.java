package com.proyectomaven.springexample.Repositories;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.proyectomaven.springexample.Entities.Productos;

@Repository
public interface ProductosRepository extends JpaRepository<Productos, Long> {
    // Custom query to find products by price range
    @Query("SELECT p FROM Productos p WHERE p.precio BETWEEN :minPrice AND :maxPrice")
    List<Productos> findProductosByPriceRange(
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice);

    // Native query to find low stock products
    @Query(value = "SELECT * FROM productos WHERE stock < :threshold", nativeQuery = true)
    List<Productos> findLowStockProducts(@Param("threshold") Integer threshold);

    // Modify product price
    @Modifying
    @Transactional
    @Query("UPDATE Productos p SET p.precio = :newPrice WHERE p.id = :productId")
    int updateProductPrice(
            @Param("productId") Long productId,
            @Param("newPrice") BigDecimal newPrice);
}
