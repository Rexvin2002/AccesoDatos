package com.tienda.Controladores;

/**
 * Kevin Gómez Valderas 2ºDAM
 */
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tienda.Entidades.Cliente;
import com.tienda.Entidades.DetallePedido;
import com.tienda.Entidades.Pedido;
import com.tienda.Entidades.Producto;
import com.tienda.Repositorios.ClienteRepository;
import com.tienda.Repositorios.DetallePedidoRepository;
import com.tienda.Repositorios.PedidoRepository;
import com.tienda.Repositorios.ProductoRepository;

@Controller
public class WebController {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    @GetMapping("/")
    public String showMenu() {
        return "index"; // Devuelve el archivo index.html
    }

    // ================== Productos ==================
    @GetMapping("/productos")
    public String listProductos(Model model) {
        model.addAttribute("productos", productoRepository.findAll());
        return "productos"; // Nombre de la vista
    }

    @GetMapping("/productos/add")
    public String showAddProductoForm(Model model) {
        model.addAttribute("producto", new Producto());
        return "add-producto"; // Nombre de la vista
    }

    @PostMapping("/productos/add")
    public String addProducto(Producto producto) {
        productoRepository.save(producto);
        return "redirect:/productos"; // Redirigir de vuelta a la lista de productos
    }

    @GetMapping("/productos/delete/{id}")
    public String deleteProducto(@PathVariable Long id) {
        // Primero eliminar los detalles de pedido asociados
        List<DetallePedido> detalles = detallePedidoRepository.findByProductoId(id);
        if (!detalles.isEmpty()) {
            detallePedidoRepository.deleteAll(detalles);
        }

        // Ahora sí podemos eliminar el producto
        productoRepository.deleteById(id);
        return "redirect:/productos";
    }

    // ================== Clientes ==================
    @GetMapping("/clientes")
    public String listClientes(Model model) {
        model.addAttribute("clientes", clienteRepository.findAll());
        return "clientes";
    }

    @GetMapping("/clientes/add")
    public String showAddClienteForm(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "add-cliente";
    }

    @PostMapping("/clientes/add")
    public String addCliente(Cliente cliente) {
        clienteRepository.save(cliente);
        return "redirect:/clientes";
    }

    @GetMapping("/clientes/delete/{id}")
    public String deleteCliente(@PathVariable Long id) {
        clienteRepository.deleteById(id);
        return "redirect:/clientes"; // Redirige de vuelta a la lista de clientes
    }

    // ================== Pedidos ==================
    // ================== Pedidos ==================
    @GetMapping("/pedidos")
    public String listPedidos(Model model) {
        model.addAttribute("pedidos", pedidoRepository.findAll());
        return "pedidos";
    }

    @GetMapping("/pedidos/add")
    public String showAddPedidoForm(Model model) {
        model.addAttribute("pedido", new Pedido());
        model.addAttribute("clientes", clienteRepository.findAll());
        model.addAttribute("productos", productoRepository.findAll());
        return "add-pedido";
    }

    @PostMapping("/pedidos/add")
    public String addPedido(@RequestParam("cliente.id") Long clienteId,
            @RequestParam("fecha") Date fecha,
            @RequestParam("productos") Long[] productosIds,
            @RequestParam("cantidades") Integer[] cantidades) {

        Cliente cliente = clienteRepository.findById(clienteId).orElseThrow();
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setFecha(fecha);
        pedido.setTotal(0.0);

        // Guardar primero el pedido para tener el ID
        pedido = pedidoRepository.save(pedido);

        double total = 0.0;
        List<DetallePedido> detalles = new ArrayList<>();

        // Crear los detalles del pedido
        for (int i = 0; i < productosIds.length; i++) {
            if (cantidades[i] > 0) {
                Producto producto = productoRepository.findById(productosIds[i]).orElseThrow();

                DetallePedido detalle = new DetallePedido();
                detalle.setPedido(pedido);
                detalle.setProducto(producto);
                detalle.setCantidad(cantidades[i]);
                detalle.setSubtotal(producto.getPrecio() * cantidades[i]);

                detalles.add(detalle);
                total += detalle.getSubtotal();

                detallePedidoRepository.save(detalle);
            }
        }

        // Actualizar el total del pedido
        pedido.setTotal(total);
        pedido.setDetalles(detalles);
        pedidoRepository.save(pedido);

        return "redirect:/pedidos";
    }

    // Eliminar Pedido
    @GetMapping("/pedidos/delete/{id}")
    public String deletePedido(@PathVariable Long id) {
        pedidoRepository.deleteById(id); // Eliminar el pedido por su ID
        return "redirect:/pedidos"; // Redirigir a la lista de pedidos
    }

    // ================== Detalles de Pedido ==================
    @GetMapping("/detalles")
    public String listDetallePedidos(Model model) {
        model.addAttribute("detalles", detallePedidoRepository.findAll());
        return "detallePedido";
    }

    @GetMapping("/detalles/add")
    public String showAddDetallePedidoForm(Model model) {
        model.addAttribute("detalle", new DetallePedido());
        model.addAttribute("productos", productoRepository.findAll());
        model.addAttribute("pedidos", pedidoRepository.findAll());
        return "add-detallePedido";
    }

    @PostMapping("/detalles/add")
    public String addDetallePedido(DetallePedido detalle) {
        detallePedidoRepository.save(detalle);
        return "redirect:/detalles";
    }

    // Añadir este nuevo método en la sección de Pedidos
    @GetMapping("/pedidos/detallePedido/{id}")
    public String verDetallePedido(@PathVariable Long id, Model model) {
        // Buscar el pedido por ID
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        // Obtener los detalles del pedido
        List<DetallePedido> detalles = detallePedidoRepository.findByProductoId(id);

        // Calcular el total
        double total = detalles.stream()
                .mapToDouble(DetallePedido::getSubtotal)
                .sum();

        // Añadir los datos al modelo
        model.addAttribute("pedido", pedido);
        model.addAttribute("detalles", detalles);
        model.addAttribute("total", total);

        return "detallePedido";
    }
}
