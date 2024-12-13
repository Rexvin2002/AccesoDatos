package com.proyectomaven.springexample.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyectomaven.springexample.Entities.Departamento;

@Repository
public interface DepartamentoRepository extends JpaRepository<Departamento, Long> {
}