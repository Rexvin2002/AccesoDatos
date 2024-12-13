package com.proyectomaven.springexample.Repositories;

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
    // Find orders by client
    @Query("SELECT p FROM Pedidos p WHERE p.cliente.id = :clientId")
    List<Pedidos> findPedidosByCliente(@Param("clientId") Long clientId);

    // Find orders within date range
    @Query("SELECT p FROM Pedidos p WHERE p.fechaPedido BETWEEN :startDate AND :endDate")
    List<Pedidos> findPedidosByDateRange(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    // Calculate total sales for a specific product
    @Query("SELECT SUM(p.total) FROM Pedidos p WHERE p.producto.id = :productoId")
    BigDecimal calculateTotalSalesByProduct(@Param("productoId") Long productoId);
}
