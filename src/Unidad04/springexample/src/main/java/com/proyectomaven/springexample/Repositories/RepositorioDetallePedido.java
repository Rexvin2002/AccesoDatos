
package com.proyectomaven.springexample.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.proyectomaven.springexample.Entities.DetallePedido;
import com.proyectomaven.springexample.Entities.Pedido;
import java.util.List;

@Repository
public interface RepositorioDetallePedido extends JpaRepository<DetallePedido, Long> {
    List<DetallePedido> findByPedido(Pedido pedido);
    List<DetallePedido> findByPedidoIdPedido(Long idPedido);
}