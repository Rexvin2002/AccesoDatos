package com.proyectomaven.springexample.Repositories;

/**
 * Kevin Gómez Valderas 2ºDAM
 */
import org.springframework.data.jpa.repository.JpaRepository;
import com.proyectomaven.springexample.Entities.Producto;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RepositorioProductos extends JpaRepository<Producto, Long> {

    // 1. Búsqueda con múltiples condiciones combinadas
    @Query("SELECT p FROM Producto p WHERE "
            + "(p.nombre LIKE %:nombre% OR p.descripcion LIKE %:descripcion%) "
            + "AND p.precio BETWEEN :precioMin AND :precioMax "
            + "AND p.stock >= :stockMin "
            + "AND (:idCategoria IS NULL OR p.categoria.idCategoria = :idCategoria)")
    List<Producto> findProductosComplejos(
            @Param("nombre") String nombre,
            @Param("descripcion") String descripcion,
            @Param("precioMin") Double precioMin,
            @Param("precioMax") Double precioMax,
            @Param("stockMin") Integer stockMin,
            @Param("idCategoria") Long idCategoria
    );

    // 2. Búsqueda con ORDER BY dinámico usando SpEL
    @Query("SELECT p FROM Producto p WHERE "
            + "p.fechaCreacion >= :fechaDesde "
            + "ORDER BY CASE WHEN :orden = 'nombre' THEN p.nombre END ASC, "
            + "CASE WHEN :orden = 'precio' THEN p.precio END ASC, "
            + "CASE WHEN :orden = 'stock' THEN p.stock END DESC")
    List<Producto> findProductosConOrden(
            @Param("fechaDesde") Date fechaDesde,
            @Param("orden") String orden
    );

    // 3. Búsqueda con comparación de fechas
    @Query("SELECT p FROM Producto p WHERE "
            + "p.fechaCreacion BETWEEN :fechaInicio AND CURRENT_DATE "
            + "AND p.stock > 0")
    List<Producto> findProductosCreadosEntreFechasConStock(
            @Param("fechaInicio") Date fechaInicio
    );

    // 4. selectCase para clasificación de productos
    @Query("SELECT p.idProducto, p.nombre, p.precio, "
            + "CASE "
            + "  WHEN p.precio < 50 THEN 'Económico' "
            + "  WHEN p.precio BETWEEN 50 AND 100 THEN 'Medio' "
            + "  ELSE 'Premium' "
            + "END AS clasificacion "
            + "FROM Producto p ORDER BY p.precio")
    List<Object[]> findProductosConClasificacionPrecio();

    // 5. Sum y groupBy para productos por categoría
    @Query("SELECT c.idCategoria, c.nombre, COUNT(p), SUM(p.stock) "
            + "FROM Producto p LEFT JOIN p.categoria c "
            + "GROUP BY c.idCategoria, c.nombre "
            + "ORDER BY COUNT(p) DESC")
    List<Object[]> findResumenProductosPorCategoria();

    // Método adicional: Productos con proveedores
    @Query("SELECT DISTINCT p FROM Producto p JOIN FETCH p.proveedores WHERE SIZE(p.proveedores) > 0")
    List<Producto> findProductosConProveedores();
}
