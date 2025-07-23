package com.proyectomaven.springexample.Repositories;

/**
 * Kevin Gómez Valderas 2ºDAM
 */
import org.springframework.data.jpa.repository.JpaRepository;
import com.proyectomaven.springexample.Entities.CategoriaProducto;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RepositorioCategoriaProducto extends JpaRepository<CategoriaProducto, Long> {

    // 1. Búsqueda con múltiples condiciones combinadas
    @Query("SELECT c FROM CategoriaProducto c WHERE "
            + "c.nombre LIKE %:nombre% "
            + "AND c.descripcion LIKE %:descripcion% "
            + "AND (SELECT COUNT(p) FROM c.productos p) >= :minProductos")
    List<CategoriaProducto> findCategoriasComplejas(
            @Param("nombre") String nombre,
            @Param("descripcion") String descripcion,
            @Param("minProductos") Long minProductos
    );

    // 2. Búsqueda con ORDER BY dinámico usando SpEL
    @Query("SELECT c FROM CategoriaProducto c ORDER BY "
            + "CASE WHEN :orden = 'nombre' THEN c.nombre END ASC, "
            + "CASE WHEN :orden = 'productos' THEN (SELECT COUNT(p) FROM c.productos p) END DESC")
    List<CategoriaProducto> findCategoriasConOrden(
            @Param("orden") String orden
    );

    // 3. selectCase para clasificación de categorías
    @Query("SELECT c.idCategoria, c.nombre, "
            + "(SELECT COUNT(p) FROM c.productos p) AS numProductos, "
            + "CASE "
            + "  WHEN (SELECT COUNT(p) FROM c.productos p) < 5 THEN 'Poca variedad' "
            + "  WHEN (SELECT COUNT(p) FROM c.productos p) BETWEEN 5 AND 15 THEN 'Variedad media' "
            + "  ELSE 'Gran variedad' "
            + "END AS clasificacion "
            + "FROM CategoriaProducto c "
            + "ORDER BY numProductos DESC")
    List<Object[]> findCategoriasConClasificacion();

    // 4. Sum y groupBy para resumen de categorías
    @Query("SELECT c.idCategoria, c.nombre, "
            + "COUNT(p), COALESCE(SUM(p.stock), 0) "
            + "FROM CategoriaProducto c LEFT JOIN c.productos p "
            + "GROUP BY c.idCategoria, c.nombre "
            + "ORDER BY COUNT(p) DESC")
    List<Object[]> findResumenCategorias();

    // 5. Categorías con productos premium (precio > promedio)
    @Query("SELECT DISTINCT c FROM CategoriaProducto c JOIN c.productos p "
            + "WHERE p.precio > (SELECT AVG(p2.precio) FROM Producto p2)")
    List<CategoriaProducto> findCategoriasConProductosPremium();

    // Método adicional: Categorías con productos en stock
    @Query("SELECT DISTINCT c FROM CategoriaProducto c JOIN c.productos p WHERE p.stock > 0")
    List<CategoriaProducto> findCategoriasConProductosEnStock();
}
