package com.proyectomaven.springexample.Controllers;

import com.proyectomaven.springexample.Entities.DetallePedido;
import com.proyectomaven.springexample.Services.DetallePedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/detalles-pedido")
public class DetallePedidoController {

    @Autowired
    private DetallePedidoService detallePedidoService;

    // Obtener detalles de pedido por ID de producto
    @GetMapping("/producto/{idProducto}")
    public List<DetallePedido> findDetallesByProductoId(@PathVariable Long idProducto) {
        return detallePedidoService.findDetallesByProductoId(idProducto);
    }

    // Obtener detalles de pedido por ID de pedido
    @GetMapping("/pedido/{idPedido}")
    public List<DetallePedido> findByPedidoId(@PathVariable Long idPedido) {
        return detallePedidoService.findByPedidoId(idPedido);
    }

    // Actualizar cantidad de un detalle de pedido
    @PutMapping("/actualizar-cantidad/{idDetalle}")
    public int updateCantidadById(@PathVariable Long idDetalle, @RequestParam Integer cantidad) {
        return detallePedidoService.updateCantidadById(cantidad, idDetalle);
    }

    // Eliminar detalle de pedido por ID de producto
    @DeleteMapping("/eliminar/producto/{idProducto}")
    public void deleteByProductoId(@PathVariable Long idProducto) {
        detallePedidoService.deleteByProductoId(idProducto);
    }
}
