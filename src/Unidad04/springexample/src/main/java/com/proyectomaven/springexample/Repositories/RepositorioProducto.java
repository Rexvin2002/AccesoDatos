
package com.proyectomaven.springexample.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.proyectomaven.springexample.Entities.Producto;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface RepositorioProducto extends JpaRepository<Producto, Long> {
    List<Producto> findByPrecioLessThan(BigDecimal precio);
    List<Producto> findByNombreContaining(String nombre);
    List<Producto> findByStockLessThan(Integer stockMinimo);
}
