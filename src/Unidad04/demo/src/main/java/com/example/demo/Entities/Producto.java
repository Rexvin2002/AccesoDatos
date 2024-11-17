package com.example.demo.Entities;

public class Producto {
    private int numero;
    private String concepto;
    private double importe;

    // Constructor
    public Producto(int numero, String concepto, double importe) {
        this.numero = numero;
        this.concepto = concepto;
        this.importe = importe;
    }

    // Getters y setters
    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }
}
