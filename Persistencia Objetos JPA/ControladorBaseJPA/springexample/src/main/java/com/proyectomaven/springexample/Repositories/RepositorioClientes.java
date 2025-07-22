package com.proyectomaven.springexample.Repositories;

/**
 * Kevin Gómez Valderas 2ºDAM
 */
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyectomaven.springexample.Entities.Clientes;

@Repository
public interface RepositorioClientes extends JpaRepository<Clientes, Long> {
    // Puedes añadir métodos personalizados aquí si los necesitas
    // Ejemplo:
    // List<Clientes> findByApellido(String apellido);
}
