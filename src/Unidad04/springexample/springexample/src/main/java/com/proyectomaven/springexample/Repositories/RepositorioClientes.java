package com.proyectomaven.springexample.Repositories;

import com.proyectomaven.springexample.Entities.Clientes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioClientes extends JpaRepository<Clientes, Long> {
}