package com.proyectomaven.springexample.Repositories;

import com.proyectomaven.springexample.Entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RepositorioClientes extends JpaRepository<Cliente, Long> {
    /*
    // Consulta nativa SQL para buscar clientes por nombre
    @Query(value = "SELECT * FROM clientes WHERE nombre = :nombre", nativeQuery = true)
    List<Cliente> findClientesByNombre(@Param("nombre") String nombre);
    
    // Consulta usando método derivado para buscar por nombre y apellido
    List<Cliente> findByNombreAndApellido(String nombre, String apellido);
    
    // Método para eliminar clientes por nombre
    @Modifying
    @Transactional
    @Query("DELETE FROM Cliente c WHERE c.nombre = :nombre")
    void deleteByNombre(@Param("nombre") String nombre);
    */
}