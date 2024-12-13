package com.proyectomaven.springexample.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyectomaven.springexample.Entities.Categorias;
import com.proyectomaven.springexample.Entities.Productos;
import com.proyectomaven.springexample.Repositories.CategoriasRepository;

@RestController
@RequestMapping("/api/categorias")
public class CategoriasController {

    @Autowired
    private CategoriasRepository categoriasRepository;

    // Obtener las categorías asociadas a un producto
    @GetMapping("/producto/{productoId}")
    public List<Categorias> getCategoriasByProducto(@PathVariable Long productoId) {
        // Aquí debemos usar el método correcto para obtener categorías por producto
        return categoriasRepository.findCategoriasByProducto(productoId);
    }

    // Obtener los productos asociados a una categoría
    @GetMapping("/{categoriaId}/productos")
    public List<Productos> getProductosByCategoria(@PathVariable Long categoriaId) {
        // Este método está correcto
        return categoriasRepository.findProductosByCategoria(categoriaId);
    }
}
