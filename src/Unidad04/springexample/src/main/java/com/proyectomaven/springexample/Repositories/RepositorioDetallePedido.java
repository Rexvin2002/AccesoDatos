
package com.proyectomaven.springexample.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.proyectomaven.springexample.Entities.DetallePedido;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RepositorioDetallePedido extends JpaRepository<DetallePedido, Long> {
    // Consulta SQL nativa para obtener detalles de pedido por producto
    @Query(value = "SELECT * FROM detalles_pedido WHERE id_producto = ?1", nativeQuery = true)
    List<DetallePedido> findDetallesByProductoId(Long idProducto);

    // Consulta con parámetros con nombre
    @Query("SELECT d FROM DetallePedido d WHERE d.pedido.idPedido = :idPedido")
    List<DetallePedido> findByPedidoId(@Param("idPedido") Long idPedido);

    // Actualización de cantidad en detalle de pedido
    @Modifying
    @Transactional
    @Query("UPDATE DetallePedido d SET d.cantidad = :cantidad WHERE d.idDetalle = :idDetalle")
    int updateCantidadById(@Param("cantidad") Integer cantidad, @Param("idDetalle") Long idDetalle);

    // Eliminación de detalle de pedido por ID de producto
    @Modifying
    @Transactional
    @Query("DELETE FROM DetallePedido d WHERE d.producto.idProducto = :idProducto")
    void deleteByProductoId(@Param("idProducto") Long idProducto);
}