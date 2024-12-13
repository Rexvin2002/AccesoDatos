package com.proyectomaven.springexample.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyectomaven.springexample.Entities.Clientes;

@Repository
public interface ClientesRepository extends JpaRepository<Clientes, Long> {
}