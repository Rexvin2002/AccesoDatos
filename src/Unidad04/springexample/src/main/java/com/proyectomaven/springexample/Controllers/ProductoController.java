package com.proyectomaven.springexample.Controllers;

import com.proyectomaven.springexample.Entities.Producto;
import com.proyectomaven.springexample.Services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    // Obtener productos con precio mayor a un valor
    @GetMapping("/precio-mayor-a/{precio}")
    public List<Producto> findProductosByPrecioGreaterThan(@PathVariable BigDecimal precio) {
        return productoService.findProductosByPrecioGreaterThan(precio);
    }

    // Obtener productos por nombre
    @GetMapping("/nombre/{nombre}")
    public List<Producto> findByNombre(@PathVariable String nombre) {
        return productoService.findByNombre(nombre);
    }

    // Actualizar precio de producto por nombre
    @PutMapping("/actualizar-precio/{nombre}")
    public int updatePrecioByNombre(@PathVariable String nombre, @RequestParam BigDecimal precio) {
        return productoService.updatePrecioByNombre(precio, nombre);
    }

    // Eliminar producto por nombre
    @DeleteMapping("/eliminar/{nombre}")
    public void deleteByNombre(@PathVariable String nombre) {
        productoService.deleteByNombre(nombre);
    }
}
