package com.example.demo.Controlador;

/**
 * Kevin Gómez Valderas           2ºDAM
 */

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

    // Agrega este método para la página de inicio
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Bienvenido a nuestra tienda");
        model.addAttribute("welcomeMessage", "Descubre nuestros productos exclusivos");
        return "home"; // Esto buscará un template llamado home.html
    }

    @GetMapping("/contacto")
    public String contacto(Model model) {
        model.addAttribute("mensaje", "Contáctenos para más información");
        model.addAttribute("email", "info@ejemplo.com");
        model.addAttribute("telefono", "+34 666 777 888");
        return "contacto";
    }

    @GetMapping("/productos")
    public String productos(Model model) {
        model.addAttribute("productos", productoRepository.findAll());
        model.addAttribute("productoDestacado", new Producto(4, "Portátil Gaming", 1499.99));
        return "productos";
    }
}