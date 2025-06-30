package com.proyectomaven.springexample.Repositories;

/**
 * Kevin Gómez Valderas           2ºDAM
 */

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.proyectomaven.springexample.Entities.Pedidos;

@Repository
public interface PedidosRepository extends JpaRepository<Pedidos, Long> {
    @Query("SELECT p FROM Pedidos p WHERE p.cliente.id = :clienteId")
    List<Pedidos> findByClienteId(@Param("clienteId") Long clienteId);

    @Query("SELECT p FROM Pedidos p WHERE p.fechaPedido = :fechaPedido")
    List<Pedidos> findByFechaPedido(@Param("fechaPedido") LocalDate fechaPedido);

    @Query("SELECT p FROM Pedidos p WHERE p.total > :total")
    List<Pedidos> findByTotalGreaterThan(@Param("total") BigDecimal total);

    @Query("SELECT p FROM Pedidos p WHERE p.producto.id = :productoId")
    List<Pedidos> findByProductoId(@Param("productoId") Long productoId);

    @Query("SELECT COUNT(p) FROM Pedidos p WHERE p.cliente.id = :clienteId")
    long countByClienteId(@Param("clienteId") Long clienteId);

    @Query("SELECT SUM(p.total) FROM Pedidos p WHERE p.cliente.id = :clienteId")
    BigDecimal sumTotalByClienteId(@Param("clienteId") Long clienteId);

    @Query("SELECT p FROM Pedidos p WHERE p.fechaPedido BETWEEN :startDate AND :endDate")
    List<Pedidos> findByFechaPedidoBetween(@Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
}
