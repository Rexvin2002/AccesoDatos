package com.tienda.Repositorios;

/**
 * Kevin Gómez Valderas 2ºDAM
 */
import org.springframework.data.jpa.repository.JpaRepository;
import com.tienda.Entidades.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
