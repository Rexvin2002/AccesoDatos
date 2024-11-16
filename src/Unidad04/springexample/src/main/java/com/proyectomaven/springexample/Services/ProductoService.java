package com.proyectomaven.springexample.Services;

import com.proyectomaven.springexample.Entities.Producto;
import com.proyectomaven.springexample.Repositories.RepositorioProducto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private RepositorioProducto productoRepository;

    // Obtener productos con precio mayor a un valor
    public List<Producto> findProductosByPrecioGreaterThan(BigDecimal precio) {
        return productoRepository.findProductosByPrecioGreaterThan(precio);
    }

    // Obtener productos por nombre
    public List<Producto> findByNombre(String nombre) {
        return productoRepository.findByNombre(nombre);
    }

    // Actualizar precio de producto por nombre
    public int updatePrecioByNombre(BigDecimal precio, String nombre) {
        return productoRepository.updatePrecioByNombre(precio, nombre);
    }

    // Eliminar producto por nombre
    public void deleteByNombre(String nombre) {
        productoRepository.deleteByNombre(nombre);
    }
}
