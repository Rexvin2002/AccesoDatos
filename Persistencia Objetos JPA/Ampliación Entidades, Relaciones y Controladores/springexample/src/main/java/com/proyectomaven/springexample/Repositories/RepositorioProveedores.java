package com.proyectomaven.springexample.Repositories;

/**
 * Kevin Gómez Valderas 2ºDAM
 */
import com.proyectomaven.springexample.Entities.Proveedor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RepositorioProveedores extends JpaRepository<Proveedor, Long> {

    // Método personalizado para buscar proveedores por nombre que contenga un string
    @Query("SELECT p FROM Proveedor p WHERE p.nombre LIKE %:nombre%")
    List<Proveedor> buscarPorNombreSimilar(@Param("nombre") String nombre);

    // Método para contar productos de un proveedor
    @Query(value = "SELECT COUNT(*) FROM productos_proveedores WHERE id_proveedor = :idProveedor",
            nativeQuery = true)
    int contarProductosPorProveedor(@Param("idProveedor") Long idProveedor);
}
