package com.proyectomaven.springexample.Controllers;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proyectomaven.springexample.Entities.Productos;
import com.proyectomaven.springexample.Repositories.ProductosRepository;

@RestController
@RequestMapping("/api/productos")
public class ProductosController {
    @Autowired
    private ProductosRepository productosRepository;

    @GetMapping
    public List<Productos> getAllProductos() {
        return productosRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Productos> getProductoById(@PathVariable Long id) {
        Optional<Productos> producto = productosRepository.findById(id);
        return producto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Productos createProducto(@RequestBody Productos producto) {
        return productosRepository.save(producto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Productos> updateProducto(@PathVariable Long id, @RequestBody Productos productoDetails) {
        Optional<Productos> producto = productosRepository.findById(id);
        if (producto.isPresent()) {
            Productos updatedProducto = producto.get();
            updatedProducto.setNombre(productoDetails.getNombre());
            updatedProducto.setPrecio(productoDetails.getPrecio());
            updatedProducto.setStock(productoDetails.getStock());
            return ResponseEntity.ok(productosRepository.save(updatedProducto));
        }
        return ResponseEntity.notFound().build();
    }

    // Custom endpoints using repository methods
    @GetMapping("/price-range")
    public List<Productos> getProductosByPriceRange(
            @RequestParam BigDecimal minPrice,
            @RequestParam BigDecimal maxPrice) {
        return productosRepository.findProductosByPriceRange(minPrice, maxPrice);
    }

    @GetMapping("/low-stock")
    public List<Productos> getLowStockProducts(@RequestParam Integer threshold) {
        return productosRepository.findLowStockProducts(threshold);
    }
}
