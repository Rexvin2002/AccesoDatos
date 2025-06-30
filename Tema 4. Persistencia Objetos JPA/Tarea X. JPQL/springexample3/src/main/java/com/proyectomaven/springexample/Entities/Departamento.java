package com.proyectomaven.springexample.Entities;

/**
 * Kevin Gómez Valderas           2ºDAM
 */

import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "departamentos")
public class Departamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_depto")
    private String nomDepto;

    @Column(name = "ubicacion")
    private String ubicacion;

    @Column(name = "presupuesto")
    private Double presupuesto;

    // Relación con Empleados
    @OneToMany(mappedBy = "departamento")
    private List<Empleado> empleados;

    // Constructores
    public Departamento() {
    }

    public Departamento(String nomDepto, String ubicacion, Double presupuesto) {
        this.nomDepto = nomDepto;
        this.ubicacion = ubicacion;
        this.presupuesto = presupuesto;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomDepto() {
        return nomDepto;
    }

    public void setNomDepto(String nomDepto) {
        this.nomDepto = nomDepto;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Double getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(double presupuesto) {
        this.presupuesto = presupuesto;
    }

    public List<Empleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }
}