
package com.proyectomaven.springexample.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.proyectomaven.springexample.Entities.Producto;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RepositorioProducto extends JpaRepository<Producto, Long> {
    // Consulta SQL nativa con parámetros posicionales
    @Query(value = "SELECT * FROM productos WHERE precio > ?1", nativeQuery = true)
    List<Producto> findProductosByPrecioGreaterThan(BigDecimal precio);

    // Consulta con parámetros con nombre
    @Query("SELECT p FROM Producto p WHERE p.nombre = :nombre")
    List<Producto> findByNombre(@Param("nombre") String nombre);

    // Actualización de precios con parámetros con nombre
    @Modifying
    @Transactional
    @Query("UPDATE Producto p SET p.precio = :precio WHERE p.nombre = :nombre")
    int updatePrecioByNombre(@Param("precio") BigDecimal precio, @Param("nombre") String nombre);

    // Eliminación de productos por nombre
    @Modifying
    @Transactional
    @Query("DELETE FROM Producto p WHERE p.nombre = :nombre")
    void deleteByNombre(@Param("nombre") String nombre);
}
