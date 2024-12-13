package com.proyectomaven.springexample.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.proyectomaven.springexample.Entities.Categorias;
import com.proyectomaven.springexample.Entities.Productos;

@Repository
public interface CategoriasRepository extends JpaRepository<Categorias, Long> {
    // Find categories by product
    @Query("SELECT c FROM Categorias c JOIN c.productos p WHERE p.id = :productoId")
    List<Categorias> findCategoriasByProducto(@Param("productoId") Long productoId);

    // Find products in a specific category
    @Query("SELECT p FROM Productos p JOIN p.categorias c WHERE c.id = :categoriaId")
    List<Productos> findProductosByCategoria(@Param("categoriaId") Long categoriaId);
}
