package com.proyectomaven.springexample.Entities;

/**
 * Kevin Gómez Valderas 2ºDAM
 */
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "proveedores")
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProveedor;

    private String nombre;
    private String contacto;

    @ManyToMany
    @JoinTable(
            name = "productos_proveedores",
            joinColumns = @JoinColumn(name = "id_proveedor"),
            inverseJoinColumns = @JoinColumn(name = "id_producto")
    )
    private Set<Producto> productos = new HashSet<>();

    public Long getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Long idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public Set<Producto> getProductos() {
        return productos;
    }

    public void setProductos(Set<Producto> productos) {
        this.productos = productos;
    }

    public void agregarProducto(Producto producto) {
        this.productos.add(producto);
        producto.getProveedores().add(this);
    }

    public void removerProducto(Producto producto) {
        this.productos.remove(producto);
        producto.getProveedores().remove(this);
    }

}
