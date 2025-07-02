package com.example.demo.Repositories;

/**
 * Kevin Gómez Valderas           2ºDAM
 */

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.example.demo.Entities.Producto;

@Repository
public class ProductoRepository {
    private List<Producto> productos;

    public ProductoRepository() {
        productos = new ArrayList<>();
        // Datos de prueba
        productos.add(new Producto(1, "Ordenador", 999.99));
        productos.add(new Producto(2, "Teléfono", 599.99));
        productos.add(new Producto(3, "Tablet", 399.99));
    }

    public List<Producto> findAll() {
        return productos;
    }
}
