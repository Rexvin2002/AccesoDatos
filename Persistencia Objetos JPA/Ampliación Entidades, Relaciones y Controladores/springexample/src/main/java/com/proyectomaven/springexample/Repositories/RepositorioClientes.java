package com.proyectomaven.springexample.Repositories;

/**
 * Kevin Gómez Valderas 2ºDAM
 */
import org.springframework.data.jpa.repository.JpaRepository;
import com.proyectomaven.springexample.Entities.Clientes;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RepositorioClientes extends JpaRepository<Clientes, Long> {

    // Consulta JPQL con parámetros nombrados
    @Query("SELECT c FROM Clientes c WHERE c.nombre = :nombre AND c.apellido = :apellido")
    List<Clientes> findByNombreCompleto(@Param("nombre") String nombre, @Param("apellido") String apellido);

    // Consulta nativa
    @Query(value = "SELECT * FROM clientes WHERE fecha_registro > :fecha", nativeQuery = true)
    List<Clientes> findClientesRegistradosDespuesDe(@Param("fecha") Date fecha);

    // Consulta de actualización
    @Modifying
    @Query("UPDATE Clientes c SET c.direccion = :direccion WHERE c.idCliente = :id")
    int actualizarDireccion(@Param("id") Long id, @Param("direccion") String direccion);
}
