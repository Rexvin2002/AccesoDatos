package com.example.demo.Controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.Entities.Producto;

// Controlador para Thymeleaf vistas
@Controller
@RequestMapping("/producto")
public class ProductController {

    // Mostrar detalle de un producto
    @GetMapping("/detalle")
    public String mostrarProducto(Model model) {
        Producto producto = new Producto(1, "Ordenador", 200);
        model.addAttribute("objeto", producto);
        return "producto/detalle_producto"; // Archivo detalle_producto.html
    }

    // Listar productos
    @GetMapping("/lista")
    public String listarProductos(Model model) {
        List<Producto> productos = List.of(
                new Producto(1, "Ordenador", 200),
                new Producto(2, "Teléfono", 150),
                new Producto(3, "Teclado", 50));
        model.addAttribute("lista", productos);
        return "producto/lista_productos"; // Archivo lista_productos.html
    }
}