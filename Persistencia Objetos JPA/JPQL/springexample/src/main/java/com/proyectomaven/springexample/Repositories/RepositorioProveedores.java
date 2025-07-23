package com.proyectomaven.springexample.Repositories;

/**
 * Kevin Gómez Valderas 2ºDAM
 */
import com.proyectomaven.springexample.Entities.Proveedor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RepositorioProveedores extends JpaRepository<Proveedor, Long> {

    // Método personalizado para buscar proveedores por nombre que contenga un string
    @Query("SELECT p FROM Proveedor p WHERE p.nombre LIKE %:nombre%")
    List<Proveedor> buscarPorNombreSimilar(@Param("nombre") String nombre);

    // Método para contar productos de un proveedor
    @Query(value = "SELECT COUNT(*) FROM productos_proveedores WHERE id_proveedor = :idProveedor",
            nativeQuery = true)
    int contarProductosPorProveedor(@Param("idProveedor") Long idProveedor);

    // 1. Búsqueda con múltiples condiciones combinadas
    @Query("SELECT p FROM Proveedor p WHERE "
            + "p.nombre LIKE %:nombre% "
            + "AND p.contacto LIKE %:contacto% "
            + "AND (SELECT COUNT(prod) FROM p.productos prod) >= :minProductos")
    List<Proveedor> findProveedoresComplejos(
            @Param("nombre") String nombre,
            @Param("contacto") String contacto,
            @Param("minProductos") Long minProductos
    );

    // 2. Búsqueda con ORDER BY dinámico usando SpEL
    @Query("SELECT p FROM Proveedor p ORDER BY "
            + "CASE WHEN :orden = 'nombre' THEN p.nombre END ASC, "
            + "CASE WHEN :orden = 'contacto' THEN p.contacto END ASC, "
            + "CASE WHEN :orden = 'productos' THEN (SELECT COUNT(prod) FROM p.productos prod) END DESC")
    List<Proveedor> findProveedoresConOrden(
            @Param("orden") String orden
    );

    // 3. selectCase para clasificación de proveedores
    @Query("SELECT p.idProveedor, p.nombre, "
            + "(SELECT COUNT(prod) FROM p.productos prod) AS numProductos, "
            + "CASE "
            + "  WHEN (SELECT COUNT(prod) FROM p.productos prod) < 5 THEN 'Pequeño' "
            + "  WHEN (SELECT COUNT(prod) FROM p.productos prod) BETWEEN 5 AND 10 THEN 'Mediano' "
            + "  ELSE 'Grande' "
            + "END AS clasificacion "
            + "FROM Proveedor p "
            + "ORDER BY numProductos DESC")
    List<Object[]> findProveedoresConClasificacion();

    // 4. Sum y groupBy para resumen de proveedores
    @Query("SELECT p.idProveedor, p.nombre, "
            + "COUNT(prod), COALESCE(SUM(prod.precio), 0) "
            + "FROM Proveedor p LEFT JOIN p.productos prod "
            + "GROUP BY p.idProveedor, p.nombre "
            + "ORDER BY COUNT(prod) DESC")
    List<Object[]> findResumenProveedores();

    // 5. Proveedores con productos en rango de precio
    @Query("SELECT DISTINCT p FROM Proveedor p JOIN p.productos prod "
            + "WHERE prod.precio BETWEEN :precioMin AND :precioMax")
    List<Proveedor> findProveedoresConProductosEnRangoPrecio(
            @Param("precioMin") Double precioMin,
            @Param("precioMax") Double precioMax
    );

    // Método adicional: Proveedores sin productos
    @Query("SELECT p FROM Proveedor p WHERE SIZE(p.productos) = 0")
    List<Proveedor> findProveedoresSinProductos();
}
