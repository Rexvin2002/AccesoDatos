package com.tienda.Repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tienda.Entidades.DetallePedido;

public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Long> {

    List<DetallePedido> findByProductoId(Long productoId);

}
