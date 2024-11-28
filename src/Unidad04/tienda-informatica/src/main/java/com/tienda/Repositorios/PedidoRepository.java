package com.tienda.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tienda.Entidades.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}