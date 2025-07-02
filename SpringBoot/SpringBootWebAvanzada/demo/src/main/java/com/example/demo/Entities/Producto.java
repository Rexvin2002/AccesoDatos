package com.example.demo.Entities;

/**
 * Kevin Gómez Valderas           2ºDAM
 */

public class Producto {
    private Integer id;
    private String concepto;
    private Double importe;

    public Producto(Integer id, String concepto, Double importe) {
        this.id = id;
        this.concepto = concepto;
        this.importe = importe;
    }

    // Getters y setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }
}
