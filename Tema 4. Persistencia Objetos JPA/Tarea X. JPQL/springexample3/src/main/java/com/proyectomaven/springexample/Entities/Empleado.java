package com.proyectomaven.springexample.Entities;

/**
 * Kevin Gómez Valderas           2ºDAM
 */

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "empleados")
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Column(name = "salario")
    private Double salario;

    @Column(name = "fecha_contratacion")
    @Temporal(TemporalType.DATE)
    private LocalDate fechaContratacion;

    // Relación con Departamento
    @ManyToOne
    @JoinColumn(name = "departamento_id")
    private Departamento departamento;

    // Constructores
    public Empleado() {
    }

    public Empleado(String nombre, String apellido, Double salario, LocalDate fechaContratacion,
            Departamento departamento) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.salario = salario;
        this.fechaContratacion = fechaContratacion;
        this.departamento = departamento;
    }

    // Getters y Setters
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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    public LocalDate getFechaContratacion() {
        return fechaContratacion;
    }

    public void setFechaContratacion(LocalDate fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }
}