package com.example.demo.Controllers;

// Controlador REST para endpoints API

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
class ApiProductController {

    // Respuesta JSON
    @GetMapping("/users")
    public Map<String, Object> pruebas() {
        Map<String, Object> body = new HashMap<>();
        body.put("title", "titulo hola mundo");
        body.put("nombre", "nombre");
        body.put("apellidos", "apellidos");
        return body;
    }

    // Crear recurso temporal (prueba)
    @PostMapping("/create")
    public String postMethodName(@RequestBody String entity) {
        return entity.replace("Gomez", "Gómez");
    }
}
