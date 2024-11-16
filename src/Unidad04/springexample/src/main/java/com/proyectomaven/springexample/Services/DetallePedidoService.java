package com.proyectomaven.springexample.Services;

import com.proyectomaven.springexample.Entities.DetallePedido;
import com.proyectomaven.springexample.Repositories.RepositorioDetallePedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetallePedidoService {

    @Autowired
    private RepositorioDetallePedido detallePedidoRepository;

    // Obtener detalles de pedido por ID de producto
    public List<DetallePedido> findDetallesByProductoId(Long idProducto) {
        return detallePedidoRepository.findDetallesByProductoId(idProducto);
    }

    // Obtener detalles de pedido por ID de pedido
    public List<DetallePedido> findByPedidoId(Long idPedido) {
        return detallePedidoRepository.findByPedidoId(idPedido);
    }

    // Actualizar cantidad de un detalle de pedido
    public int updateCantidadById(Integer cantidad, Long idDetalle) {
        return detallePedidoRepository.updateCantidadById(cantidad, idDetalle);
    }

    // Eliminar detalle de pedido por ID de producto
    public void deleteByProductoId(Long idProducto) {
        detallePedidoRepository.deleteByProductoId(idProducto);
    }
}
