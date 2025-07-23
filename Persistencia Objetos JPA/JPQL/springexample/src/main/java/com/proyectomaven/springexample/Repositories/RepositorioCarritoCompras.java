package com.proyectomaven.springexample.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.proyectomaven.springexample.Entities.CarritoCompras;
import java.util.Date;
import java.util.List;

public interface RepositorioCarritoCompras extends JpaRepository<CarritoCompras, Long> {

    // 1. Búsqueda con múltiples condiciones combinadas
    @Query("SELECT c FROM CarritoCompras c JOIN c.cliente cl WHERE " +
           "(:idCliente IS NULL OR cl.idCliente = :idCliente) " +
           "AND (:nombreCliente IS NULL OR cl.nombre LIKE %:nombreCliente%)")
    List<CarritoCompras> findCarritosComplejos(
        @Param("idCliente") Long idCliente,
        @Param("nombreCliente") String nombreCliente
    );
    
    // 2. Búsqueda con ORDER BY dinámico usando SpEL
    @Query("SELECT c FROM CarritoCompras c JOIN c.cliente cl ORDER BY " +
           "CASE WHEN :orden = 'cliente' THEN cl.nombre END ASC, " +
           "CASE WHEN :orden = 'fecha' THEN cl.fechaRegistro END DESC")
    List<CarritoCompras> findCarritosConOrden(
        @Param("orden") String orden
    );
    
    // 3. selectCase para clasificación de carritos por antigüedad del cliente
    @Query("SELECT c.idCarrito, cl.nombre, cl.fechaRegistro, " +
           "CASE WHEN cl.fechaRegistro > :fechaLimite THEN 'Cliente nuevo' " +
           "ELSE 'Cliente antiguo' END AS tipoCliente " +
           "FROM CarritoCompras c JOIN c.cliente cl " +
           "ORDER BY cl.fechaRegistro DESC")
    List<Object[]> findCarritosConClasificacionCliente(
        @Param("fechaLimite") Date fechaLimite
    );
    
    // 4. Join para obtener carritos con información de cliente
    @Query("SELECT c.idCarrito, cl.idCliente, cl.nombre, cl.email " +
           "FROM CarritoCompras c JOIN c.cliente cl " +
           "ORDER BY cl.nombre ASC")
    List<Object[]> findCarritosConInfoCliente();
    
    // 5. Contar carritos por tipo de cliente
    @Query("SELECT " +
           "CASE WHEN cl.fechaRegistro > :fechaLimite THEN 'Nuevo' " +
           "ELSE 'Antiguo' END AS tipoCliente, " +
           "COUNT(c) " +
           "FROM CarritoCompras c JOIN c.cliente cl " +
           "GROUP BY tipoCliente")
    List<Object[]> countCarritosPorTipoCliente(
        @Param("fechaLimite") Date fechaLimite
    );
    
    // Método adicional: Carritos de clientes con email
    @Query("SELECT c FROM CarritoCompras c JOIN c.cliente cl WHERE cl.email IS NOT NULL")
    List<CarritoCompras> findCarritosConClientesConEmail();
}