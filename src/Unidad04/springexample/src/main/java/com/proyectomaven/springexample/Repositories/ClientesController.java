package com.proyectomaven.springexample.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyectomaven.springexample.Entities.Clientes;

@Repository
public interface ClientesController extends JpaRepository<Clientes, Long> {

    // Método para encontrar un cliente por nombre
    Optional<com.proyectomaven.springexample.Entities.Clientes> findByNombre(String nombre);

    // Método para encontrar todos los clientes con un apellido específico
    List<Clientes> findByApellido(String apellido);

    // Método para buscar clientes cuyo nombre contenga una palabra o fragmento
    List<Clientes> findByNombreContaining(String fragmento);

    // Método para buscar clientes por nombre y apellido específicos
    Optional<Clientes> findByNombreAndApellido(String nombre, String apellido);

    // Método para contar el número de clientes con un apellido dado
    long countByApellido(String apellido);

    // Método para eliminar un cliente por nombre
    void deleteByNombre(String nombre);
}
