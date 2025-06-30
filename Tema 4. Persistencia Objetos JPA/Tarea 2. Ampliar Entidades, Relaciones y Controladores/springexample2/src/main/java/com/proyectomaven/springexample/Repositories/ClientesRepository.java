package com.proyectomaven.springexample.Repositories;

/**
 * Kevin Gómez Valderas           2ºDAM
 */

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.proyectomaven.springexample.Entities.Clientes;

@Repository
public interface ClientesRepository extends JpaRepository<Clientes, Long> {

    // Consulta personalizada con JPQL: Buscar clientes por nombre y apellido
    @Query("SELECT c FROM Clientes c WHERE c.nombre = :nombre AND c.apellido = :apellido")
    List<Clientes> findByNombreAndApellidoJPQL(String nombre, String apellido);

    // Consulta personalizada con JPQL: Buscar clientes cuyo nombre contenga una
    // palabra clave
    @Query("SELECT c FROM Clientes c WHERE c.nombre LIKE %:keyword%")
    List<Clientes> findByNombreContaining(@Param("keyword") String keyword);

    // Contar la cantidad de clientes que tienen una dirección específica
    @Query("SELECT COUNT(c) FROM Clientes c WHERE c.direccion = :direccion")
    long countByDireccion(@Param("direccion") String direccion);

    // Actualizar la dirección de un cliente por ID
    @Query("UPDATE Clientes c SET c.direccion = :direccion WHERE c.idEmpleado = :idEmpleado")
    int updateDireccion(@Param("idEmpleado") Long idEmpleado, @Param("direccion") String direccion);

    // Eliminar un cliente por nombre y apellido
    @Query("DELETE FROM Clientes c WHERE c.nombre = :nombre AND c.apellido = :apellido")
    void deleteByNombreAndApellido(@Param("nombre") String nombre, @Param("apellido") String apellido);

    // Buscar clientes que viven en una dirección específica
    @Query("SELECT c FROM Clientes c WHERE c.direccion = :direccion")
    List<Clientes> findByDireccionExact(@Param("direccion") String direccion);
}
