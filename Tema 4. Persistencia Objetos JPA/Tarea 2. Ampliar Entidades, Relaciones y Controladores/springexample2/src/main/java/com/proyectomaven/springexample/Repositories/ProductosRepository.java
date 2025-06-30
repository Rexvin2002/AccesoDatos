package com.proyectomaven.springexample.Repositories;

/**
 * Kevin Gómez Valderas           2ºDAM
 */

import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.proyectomaven.springexample.Entities.Productos;

@Repository
public interface ProductosRepository extends JpaRepository<Productos, Long> {
        // Obtener productos por nombre
        @Query("SELECT p FROM Productos p WHERE p.nombre LIKE %:nombre%")
        List<Productos> findByNombre(@Param("nombre") String nombre);

        // Obtener productos por precio mayor que un valor dado
        @Query("SELECT p FROM Productos p WHERE p.precio > :precio")
        List<Productos> findByPrecioGreaterThan(@Param("precio") BigDecimal precio);

        // Obtener productos por precio menor que un valor dado
        @Query("SELECT p FROM Productos p WHERE p.precio < :precio")
        List<Productos> findByPrecioLessThan(@Param("precio") BigDecimal precio);

        // Obtener productos por stock disponible mayor que un valor
        @Query("SELECT p FROM Productos p WHERE p.stock > :stock")
        List<Productos> findByStockGreaterThan(@Param("stock") Integer stock);

        // Obtener el total del stock de todos los productos
        @Query("SELECT SUM(p.stock) FROM Productos p")
        Integer sumTotalStock();

        // Contar productos por nombre
        @Query("SELECT COUNT(p) FROM Productos p WHERE p.nombre = :nombre")
        long countByNombre(@Param("nombre") String nombre);
}
