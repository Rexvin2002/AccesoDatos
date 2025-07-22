package com.proyectomaven.springexample;

/**
 * Kevin Gómez Valderas 2ºDAM
 */
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import com.proyectomaven.springexample.Entities.CategoriaProducto;
import com.proyectomaven.springexample.Entities.Clientes;
import com.proyectomaven.springexample.Entities.DetallePedido;
import com.proyectomaven.springexample.Entities.Pedido;
import com.proyectomaven.springexample.Entities.Producto;
import com.proyectomaven.springexample.Repositories.RepositorioCategorias;
import com.proyectomaven.springexample.Repositories.RepositorioClientes;
import com.proyectomaven.springexample.Repositories.RepositorioDetallesPedido;
import com.proyectomaven.springexample.Repositories.RepositorioPedidos;
import com.proyectomaven.springexample.Repositories.RepositorioProductos;

@SpringBootApplication
public class SpringexampleApplication implements CommandLineRunner {

    @Autowired
    private RepositorioClientes clientRepository;

    @Autowired
    private RepositorioCategorias categoriaRepository;

    @Autowired
    private RepositorioProductos productoRepository;

    @Autowired
    private RepositorioPedidos pedidoRepository;

    @Autowired
    private RepositorioDetallesPedido detallePedidoRepository;

    public static void main(String[] args) {
        SpringApplication.run(SpringexampleApplication.class, args);
    }

    @Override
    @Transactional
    public void run(String... args) {
        inicializarDatos();
        realizarOperacionesEjemplo();
    }

    private void inicializarDatos() {
        // Datos iniciales simplificados
        Clientes cliente = new Clientes("Pepe", "Pérez", "Calle Mayor 1");
        clientRepository.save(cliente);

        CategoriaProducto categoria = new CategoriaProducto("Electrónica", "Productos electrónicos");
        categoriaRepository.save(categoria);

        Producto producto = new Producto("Smartphone", "Teléfono inteligente", 599.99, 50);
        producto.setCategoria(categoria);
        productoRepository.save(producto);
    }

    private void realizarOperacionesEjemplo() {
        Clientes cliente = clientRepository.findById(1L).orElseThrow();
        Pedido pedido = new Pedido(cliente, new Date(), "PENDIENTE");
        pedidoRepository.save(pedido);

        Producto producto = productoRepository.findById(1L).orElseThrow();
        DetallePedido detalle = new DetallePedido(pedido, producto, 2, producto.getPrecio());
        detallePedidoRepository.save(detalle);

        double total = detalle.getCantidad() * detalle.getPrecioUnitario();
        pedido.setTotal(total);
        pedidoRepository.save(pedido);

        System.out.println("Pedido creado con ID: " + pedido.getIdPedido() + " y total: " + total);
    }
}
