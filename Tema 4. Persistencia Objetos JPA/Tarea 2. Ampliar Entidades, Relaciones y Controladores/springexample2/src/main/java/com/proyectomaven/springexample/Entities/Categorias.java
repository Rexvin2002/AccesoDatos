package com.proyectomaven.springexample.Entities;

/**
 * Kevin Gómez Valderas           2ºDAM
 */

import java.util.Set;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

// Categorias Entity - Many to Many with Productos
@Entity
@Table(name = "categorias")
public class Categorias {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", length = 50, unique = true)
    private String nombre;

    // Many-to-Many with Productos
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "producto_categoria", joinColumns = @JoinColumn(name = "categoria_id"), inverseJoinColumns = @JoinColumn(name = "producto_id"), uniqueConstraints = @UniqueConstraint(columnNames = {
            "categoria_id", "producto_id" }))
    private Set<Productos> productos;

    // Constructors
    public Categorias() {
    }

    public Categorias(String nombre) {
        this.nombre = nombre;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Productos> getProductos() {
        return productos;
    }

    public void setProductos(Set<Productos> productos) {
        this.productos = productos;
    }
}