package com.example.demo.Controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.Entities.Producto;
import com.example.demo.Repositories.ProductoRepository;

@Controller
public class WebController {

    @Autowired
    private ProductoRepository productoRepository;

    @GetMapping("/contacto")
    public String contacto(Model model) {
        model.addAttribute("mensaje", "Contáctenos para más información");
        model.addAttribute("email", "info@ejemplo.com");
        model.addAttribute("telefono", "+34 666 777 888");
        return "contacto";
    }

    @GetMapping("/productos")
    public String productos(Model model) {
        // Enviar lista de productos al template
        model.addAttribute("productos", productoRepository.findAll());
        // Enviar un producto individual como ejemplo
        model.addAttribute("productoDestacado", new Producto(4, "Portátil Gaming", 1499.99));
        return "productos";
    }
}