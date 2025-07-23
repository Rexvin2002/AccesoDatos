package com.proyectomaven.springexample.Repositories;

/**
 * Kevin Gómez Valderas 2ºDAM
 */
import org.springframework.data.jpa.repository.JpaRepository;
import com.proyectomaven.springexample.Entities.Pedido;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RepositorioPedidos extends JpaRepository<Pedido, Long> {

    // 1. Búsqueda con múltiples condiciones combinadas
    @Query("SELECT p FROM Pedido p WHERE "
            + "(:idCliente IS NULL OR p.cliente.idCliente = :idCliente) "
            + "AND (:estado IS NULL OR p.estado = :estado) "
            + "AND p.fechaPedido BETWEEN :fechaDesde AND :fechaHasta "
            + "AND p.total BETWEEN :totalMin AND :totalMax")
    List<Pedido> findPedidosComplejos(
            @Param("idCliente") Long idCliente,
            @Param("estado") String estado,
            @Param("fechaDesde") Date fechaDesde,
            @Param("fechaHasta") Date fechaHasta,
            @Param("totalMin") Double totalMin,
            @Param("totalMax") Double totalMax
    );

    // 2. Búsqueda con ORDER BY dinámico usando SpEL
    @Query("SELECT p FROM Pedido p ORDER BY "
            + "CASE WHEN :orden = 'fecha' THEN p.fechaPedido END DESC, "
            + "CASE WHEN :orden = 'total' THEN p.total END DESC, "
            + "CASE WHEN :orden = 'cliente' THEN p.cliente.nombre END ASC")
    List<Pedido> findPedidosConOrden(
            @Param("orden") String orden
    );

    // 3. selectCase para clasificación de pedidos
    @Query("SELECT p.idPedido, p.cliente.nombre, p.total, "
            + "CASE "
            + "  WHEN p.total < 100 THEN 'Pequeño' "
            + "  WHEN p.total BETWEEN 100 AND 500 THEN 'Mediano' "
            + "  ELSE 'Grande' "
            + "END AS clasificacion "
            + "FROM Pedido p "
            + "ORDER BY p.total DESC")
    List<Object[]> findPedidosConClasificacion();

    // 4. Sum y groupBy para resumen de pedidos por cliente
    @Query("SELECT c.idCliente, c.nombre, COUNT(p), COALESCE(SUM(p.total), 0) "
            + "FROM Pedido p JOIN p.cliente c "
            + "GROUP BY c.idCliente, c.nombre "
            + "ORDER BY SUM(p.total) DESC")
    List<Object[]> findResumenPedidosPorCliente();

    // 5. Pedidos recientes usando current_date
    @Query("SELECT p FROM Pedido p WHERE "
            + "p.fechaPedido >= FUNCTION('DATE_SUB', CURRENT_DATE, :dias, 'DAY') "
            + "ORDER BY p.fechaPedido DESC")
    List<Pedido> findPedidosRecientes(
            @Param("dias") int dias
    );

    // Método adicional: Pedidos con detalles (JOIN FETCH)
    @Query("SELECT DISTINCT p FROM Pedido p JOIN FETCH p.detalles WHERE SIZE(p.detalles) > 0")
    List<Pedido> findPedidosConDetalles();
}
