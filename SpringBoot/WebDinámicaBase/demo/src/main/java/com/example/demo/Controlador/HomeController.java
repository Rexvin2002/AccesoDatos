package com.example.demo.Controlador;

/**
 * Kevin Gómez Valderas 2ºDAM
 */
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/pruebas")
    public String home(Model model) {

        // LE PASAMOS UN MODELO ( DATOS ) AL TEMPLATE
        model.addAttribute("title", "Título");
        model.addAttribute("nombre", "Pepe");
        model.addAttribute("apellido", "Gomez");

        // ESTE TEXTO QUE SE RETORNA ES EL NOMBRE DEL FICHERO/PLANTILLA WEB QUE SE
        // ABRIRÁ EN EL NAVEGADOR
        return "home"; // Debe coincidir con el nombre del archivo HTML (sin extensión).
    }
}
