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

    // 1. Varios OR, AND, LIKE mezclados
    @Query("SELECT c FROM Clientes c WHERE "
            + "(c.nombre LIKE %:nombre% OR c.apellido LIKE %:nombre%) "
            + "AND c.direccion LIKE %:direccion% "
            + "AND (c.email IS NOT NULL OR c.telefono IS NOT NULL)")
    List<Clientes> findClientesComplejo(
            @Param("nombre") String nombre,
            @Param("direccion") String direccion
    );

    // 2. ORDER BY dinámico (usando SpEL)
    @Query("SELECT c FROM Clientes c WHERE c.fechaRegistro > :fecha ORDER BY "
            + "CASE WHEN :orden = 'nombre' THEN c.nombre END ASC, "
            + "CASE WHEN :orden = 'apellido' THEN c.apellido END ASC, "
            + "CASE WHEN :orden = 'fecha' THEN c.fechaRegistro END DESC")
    List<Clientes> findClientesConOrden(
            @Param("fecha") Date fecha,
            @Param("orden") String orden
    );

    // 3. greaterThan y currentDate
    @Query("SELECT c FROM Clientes c WHERE c.fechaRegistro > :fechaMinima "
            + "AND c.fechaRegistro < CURRENT_DATE")
    List<Clientes> findClientesEntreFechas(
            @Param("fechaMinima") Date fechaMinima
    );

    // 4. selectCase (Opcional)
    @Query("SELECT c.idCliente, c.nombre, "
            + "CASE WHEN c.fechaRegistro > :fechaLimite THEN 'Nuevo' ELSE 'Antiguo' END "
            + "FROM Clientes c ORDER BY c.nombre")
    List<Object[]> findClientesConCategoria(
            @Param("fechaLimite") Date fechaLimite
    );

    // 5. Sum() y groupBy() (Opcional)
    @Query("SELECT c.idCliente, c.nombre, SUM(COALESCE(p.total, 0)) "
            + "FROM Clientes c LEFT JOIN c.pedidos p "
            + "GROUP BY c.idCliente, c.nombre "
            + "ORDER BY SUM(p.total) DESC")
    List<Object[]> findTotalPedidosPorCliente();
}
