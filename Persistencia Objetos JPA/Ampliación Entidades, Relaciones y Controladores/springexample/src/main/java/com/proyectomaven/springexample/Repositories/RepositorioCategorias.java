package com.proyectomaven.springexample.Repositories;

/**
 * Kevin Gómez Valderas 2ºDAM
 */
import org.springframework.data.jpa.repository.JpaRepository;
import com.proyectomaven.springexample.Entities.CategoriaProducto;

public interface RepositorioCategorias extends JpaRepository<CategoriaProducto, Long> {
}
