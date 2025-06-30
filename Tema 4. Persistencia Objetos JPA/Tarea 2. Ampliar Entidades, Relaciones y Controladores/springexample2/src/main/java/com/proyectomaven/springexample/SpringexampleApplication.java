package com.proyectomaven.springexample;

/**
 * Kevin Gómez Valderas           2ºDAM
 */

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import com.proyectomaven.springexample.Entities.Categorias;
import com.proyectomaven.springexample.Entities.Clientes;
import com.proyectomaven.springexample.Entities.Pedidos;
import com.proyectomaven.springexample.Entities.Productos;
import com.proyectomaven.springexample.Repositories.CategoriasRepository;
import com.proyectomaven.springexample.Repositories.ClientesRepository;
import com.proyectomaven.springexample.Repositories.PedidosRepository;
import com.proyectomaven.springexample.Repositories.ProductosRepository;

@SpringBootApplication
public class SpringexampleApplication implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(SpringexampleApplication.class);

    @Autowired
    private ClientesRepository clienteRepository;

    @Autowired
    private CategoriasRepository categoriasRepository;

    @Autowired
    private PedidosRepository pedidosRepository;

    @Autowired
    private ProductosRepository productosRepository;

    public static void main(String[] args) {
        SpringApplication.run(SpringexampleApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        funcionPruebas1();
    }

    @Transactional
    public void funcionPruebas1() {

        /*
         * CLIENTES
         */

        // 1. Buscar clientes por nombre y apellido
        String nombre = "Juan";
        String apellido = "Perez";
        List<Clientes> clientesPorNombreYApellido = clienteRepository.findByNombreAndApellidoJPQL(nombre, apellido);
        logger.info("Clientes encontrados por nombre y apellido: {}", clientesPorNombreYApellido);

        // 2. Buscar clientes cuyo nombre contenga una palabra clave
        String keyword = "Mar";
        List<Clientes> clientesPorNombreConteniendo = clienteRepository.findByNombreContaining(keyword);
        logger.info("Clientes cuyo nombre contiene '{}': {}", keyword, clientesPorNombreConteniendo);

        // 3. Contar la cantidad de clientes que tienen una dirección específica
        String direccion = "Calle Ficticia 123";
        long cantidadClientesPorDireccion = clienteRepository.countByDireccion(direccion);
        logger.info("Cantidad de clientes en dirección '{}': {}", direccion, cantidadClientesPorDireccion);

        // 4. Actualizar la dirección de un cliente por ID
        Long idEmpleado = 1L;
        String nuevaDireccion = "Calle Actualizada 456";
        int clientesActualizados = clienteRepository.updateDireccion(idEmpleado, nuevaDireccion);
        logger.info("Número de clientes actualizados con nueva dirección: {}", clientesActualizados);

        // 5. Eliminar un cliente por nombre y apellido
        String nombreAEliminar = "Ana";
        String apellidoAEliminar = "Gomez";
        clienteRepository.deleteByNombreAndApellido(nombreAEliminar, apellidoAEliminar);
        logger.info("Cliente con nombre '{}' y apellido '{}' eliminado", nombreAEliminar, apellidoAEliminar);

        // 6. Buscar clientes que viven en una dirección específica
        List<Clientes> clientesEnDireccion = clienteRepository.findByDireccionExact(direccion);
        logger.info("Clientes que viven en la dirección '{}': {}", direccion, clientesEnDireccion);

        /*
         * CATEGORIAS
         */

        // Probar findByNombre
        Categorias categoria = categoriasRepository.findByNombre("Electronics");
        System.out.println("Categoria encontrada por nombre: " + categoria);

        // Probar findByNombreContaining
        List<Categorias> categorias = categoriasRepository.findByNombreContaining("Elec");
        System.out.println("Categorias que contienen 'Elec': ");
        categorias.forEach(c -> System.out.println(c.getNombre()));

        // Probar findAllWithProductos
        List<Categorias> categoriasConProductos = categoriasRepository.findAllWithProductos();
        System.out.println("Categorias con productos: ");
        categoriasConProductos.forEach(c -> System.out.println(c.getNombre()));

        // Probar findByProductoId
        List<Categorias> categoriasPorProducto = categoriasRepository.findByProductoId(1L);
        System.out.println("Categorias asociadas al producto con id 1: ");
        categoriasPorProducto.forEach(c -> System.out.println(c.getNombre()));

        // Probar countByNombre
        long count = categoriasRepository.countByNombre("Electronics");
        System.out.println("Cantidad de categorias con nombre 'Electronics': " + count);

        /*
         * PEDIDOS
         */

        // Probar findByClienteId
        List<Pedidos> pedidosPorCliente = pedidosRepository.findByClienteId(1L);
        System.out.println("Pedidos para el cliente con ID 1:");
        pedidosPorCliente.forEach(p -> System.out.println(p.getId() + " - " + p.getTotal()));

        // Probar findByFechaPedido
        List<Pedidos> pedidosPorFecha = pedidosRepository.findByFechaPedido(LocalDate.of(2024, 12, 10));
        System.out.println("Pedidos para la fecha 2024-12-10:");
        pedidosPorFecha.forEach(p -> System.out.println(p.getId() + " - " + p.getTotal()));

        // Probar findByTotalGreaterThan
        List<Pedidos> pedidosConTotalMayor = pedidosRepository.findByTotalGreaterThan(BigDecimal.valueOf(100));
        System.out.println("Pedidos con total mayor a 100:");
        pedidosConTotalMayor.forEach(p -> System.out.println(p.getId() + " - " + p.getTotal()));

        // Probar findByProductoId
        List<Pedidos> pedidosPorProducto = pedidosRepository.findByProductoId(1L);
        System.out.println("Pedidos para el producto con ID 1:");
        pedidosPorProducto.forEach(p -> System.out.println(p.getId() + " - " + p.getTotal()));

        // Probar countByClienteId
        long countPedidos = pedidosRepository.countByClienteId(1L);
        System.out.println("Cantidad de pedidos para el cliente con ID 1: " + countPedidos);

        // Probar sumTotalByClienteId
        BigDecimal sumaTotal = pedidosRepository.sumTotalByClienteId(1L);
        System.out.println("Suma de los totales de pedidos para el cliente con ID 1: " + sumaTotal);

        // Probar findByFechaPedidoBetween
        List<Pedidos> pedidosEntreFechas = pedidosRepository.findByFechaPedidoBetween(LocalDate.of(2024, 12, 1),
                LocalDate.of(2024, 12, 10));
        System.out.println("Pedidos entre 2024-12-01 y 2024-12-10:");
        pedidosEntreFechas.forEach(p -> System.out.println(p.getId() + " - " + p.getTotal()));

        /*
         * PRODUCTOS
         */

        // Probar findByNombre
        List<Productos> productosPorNombre = productosRepository.findByNombre("Laptop");
        System.out.println("Productos que contienen 'Laptop' en su nombre:");
        productosPorNombre.forEach(p -> System.out.println(p.getId() + " - " + p.getNombre() + " - " + p.getPrecio()));

        // Probar findByPrecioGreaterThan
        List<Productos> productosPorPrecioMayor = productosRepository.findByPrecioGreaterThan(BigDecimal.valueOf(500));
        System.out.println("Productos con precio mayor a 500:");
        productosPorPrecioMayor
                .forEach(p -> System.out.println(p.getId() + " - " + p.getNombre() + " - " + p.getPrecio()));

        // Probar findByPrecioLessThan
        List<Productos> productosPorPrecioMenor = productosRepository.findByPrecioLessThan(BigDecimal.valueOf(100));
        System.out.println("Productos con precio menor a 100:");
        productosPorPrecioMenor
                .forEach(p -> System.out.println(p.getId() + " - " + p.getNombre() + " - " + p.getPrecio()));

        // Probar findByStockGreaterThan
        List<Productos> productosPorStock = productosRepository.findByStockGreaterThan(10);
        System.out.println("Productos con stock mayor a 10:");
        productosPorStock.forEach(p -> System.out.println(p.getId() + " - " + p.getNombre() + " - " + p.getStock()));

        // Probar sumTotalStock
        Integer totalStock = productosRepository.sumTotalStock();
        System.out.println("Total del stock de todos los productos: " + totalStock);

        // Probar countByNombre
        long countProductos = productosRepository.countByNombre("Laptop");
        System.out.println("Cantidad de productos con nombre 'Laptop': " + countProductos);

    }

}
