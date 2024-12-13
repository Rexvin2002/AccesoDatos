package com.proyectomaven.springexample.Controllers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proyectomaven.springexample.Entities.Pedidos;
import com.proyectomaven.springexample.Repositories.PedidosRepository;

@RestController
@RequestMapping("/api/pedidos")
public class PedidosController {
    @Autowired
    private PedidosRepository pedidosRepository;

    @GetMapping("/cliente/{clientId}")
    public List<Pedidos> getPedidosByCliente(@PathVariable Long clientId) {
        return pedidosRepository.findPedidosByCliente(clientId);
    }

    @GetMapping("/date-range")
    public List<Pedidos> getPedidosByDateRange(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        return pedidosRepository.findPedidosByDateRange(startDate, endDate);
    }

    @GetMapping("/sales/{productoId}")
    public BigDecimal getTotalSalesByProduct(@PathVariable Long productoId) {
        return pedidosRepository.calculateTotalSalesByProduct(productoId);
    }
}