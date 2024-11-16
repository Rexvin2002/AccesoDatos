
package com.proyectomaven.springexample.Repositories;

import com.proyectomaven.springexample.Entities.Cliente;
import com.proyectomaven.springexample.Entities.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RepositorioPedido extends JpaRepository<Pedido, Long> {
    List<Pedido> findByCliente(Cliente cliente);
    List<Pedido> findByEstado(Pedido.EstadoPedido estado);
    List<Pedido> findByFechaPedidoBetween(LocalDateTime inicio, LocalDateTime fin);
}