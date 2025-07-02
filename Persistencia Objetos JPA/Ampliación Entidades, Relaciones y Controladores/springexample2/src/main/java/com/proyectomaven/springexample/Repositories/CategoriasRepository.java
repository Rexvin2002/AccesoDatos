package com.proyectomaven.springexample.Repositories;

/**
 * Kevin Gómez Valderas           2ºDAM
 */

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.proyectomaven.springexample.Entities.Categorias;

@Repository
public interface CategoriasRepository extends JpaRepository<Categorias, Long> {
    @Query("SELECT c FROM Categorias c WHERE c.nombre = :nombre")
    Categorias findByNombre(@Param("nombre") String nombre);

    @Query("SELECT c FROM Categorias c WHERE c.nombre LIKE %:nombre%")
    List<Categorias> findByNombreContaining(@Param("nombre") String nombre);

    @Query("SELECT c FROM Categorias c JOIN FETCH c.productos")
    List<Categorias> findAllWithProductos();

    @Query("SELECT c FROM Categorias c JOIN c.productos p WHERE p.id = :productoId")
    List<Categorias> findByProductoId(@Param("productoId") Long productoId);

    @Query("SELECT COUNT(c) FROM Categorias c WHERE c.nombre = :nombre")
    long countByNombre(@Param("nombre") String nombre);

}
