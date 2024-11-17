package com.example.demo.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // Página principal
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Título");
        model.addAttribute("nombre", "Pepe");
        model.addAttribute("apellido", "Gomez");
        return "home"; // Debe coincidir con el nombre del archivo HTML en templates.
    }

    // Página de contacto
    @GetMapping("/contacto")
    public String contacto(Model model) {
        model.addAttribute("mensaje", "Hola desde Spring MVC");
        model.addAttribute("email", "contacto@example.com");
        model.addAttribute("telefono", "+34 123 456 789");
        return "contacto"; // Archivo contacto.html en templates.
    }

    // Otra página (por ejemplo, "sobre nosotros")
    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("descripcion", "Somos una empresa dedicada a crear soluciones innovadoras.");
        model.addAttribute("mision", "Proveer servicios de calidad.");
        model.addAttribute("vision", "Ser líderes en nuestro sector.");
        return "about"; // Archivo about.html en templates.
    }

}
