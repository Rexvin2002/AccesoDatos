package com.proyectomaven.springexample.Repositories;

/**
 * Kevin Gómez Valderas 2ºDAM
 */
import org.springframework.data.jpa.repository.JpaRepository;
import com.proyectomaven.springexample.Entities.DetallePedido;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RepositorioDetallesPedido extends JpaRepository<DetallePedido, Long> {

    // 1. Búsqueda con múltiples condiciones combinadas
    @Query("SELECT d FROM DetallePedido d WHERE "
            + "(:idPedido IS NULL OR d.pedido.idPedido = :idPedido) "
            + "AND (:idProducto IS NULL OR d.producto.idProducto = :idProducto) "
            + "AND d.cantidad BETWEEN :cantidadMin AND :cantidadMax "
            + "AND d.precioUnitario BETWEEN :precioMin AND :precioMax")
    List<DetallePedido> findDetallesComplejos(
            @Param("idPedido") Long idPedido,
            @Param("idProducto") Long idProducto,
            @Param("cantidadMin") Integer cantidadMin,
            @Param("cantidadMax") Integer cantidadMax,
            @Param("precioMin") Double precioMin,
            @Param("precioMax") Double precioMax
    );

    // 2. Búsqueda con ORDER BY dinámico usando SpEL
    @Query("SELECT d FROM DetallePedido d ORDER BY "
            + "CASE WHEN :orden = 'cantidad' THEN d.cantidad END DESC, "
            + "CASE WHEN :orden = 'precio' THEN d.precioUnitario END DESC, "
            + "CASE WHEN :orden = 'producto' THEN d.producto.nombre END ASC")
    List<DetallePedido> findDetallesConOrden(
            @Param("orden") String orden
    );

    // 3. selectCase para clasificación de detalles
    @Query("SELECT d.idDetalle, d.pedido.idPedido, d.producto.nombre, "
            + "(d.cantidad * d.precioUnitario) AS subtotal, "
            + "CASE "
            + "  WHEN (d.cantidad * d.precioUnitario) < 100 THEN 'Pequeño' "
            + "  WHEN (d.cantidad * d.precioUnitario) BETWEEN 100 AND 500 THEN 'Mediano' "
            + "  ELSE 'Grande' "
            + "END AS clasificacion "
            + "FROM DetallePedido d "
            + "ORDER BY subtotal DESC")
    List<Object[]> findDetallesConClasificacion();

    // 4. Sum y groupBy para resumen de productos vendidos
    @Query("SELECT p.idProducto, p.nombre, "
            + "SUM(d.cantidad), SUM(d.cantidad * d.precioUnitario) "
            + "FROM DetallePedido d JOIN d.producto p "
            + "GROUP BY p.idProducto, p.nombre "
            + "ORDER BY SUM(d.cantidad * d.precioUnitario) DESC")
    List<Object[]> findResumenProductosVendidos();

    // 5. Total por pedido
    @Query("SELECT p.idPedido, c.nombre, SUM(d.cantidad * d.precioUnitario) "
            + "FROM DetallePedido d JOIN d.pedido p JOIN p.cliente c "
            + "GROUP BY p.idPedido, c.nombre "
            + "ORDER BY SUM(d.cantidad * d.precioUnitario) DESC")
    List<Object[]> findTotalPorPedido();

    // Método adicional: Detalles con información completa (JOIN FETCH)
    @Query("SELECT d FROM DetallePedido d JOIN FETCH d.pedido JOIN FETCH d.producto")
    List<DetallePedido> findDetallesCompletos();
}
