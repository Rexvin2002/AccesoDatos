package com.proyectomaven.springexample.Entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPedido;
    
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
    
    @Column(name = "fecha_pedido")
    private LocalDateTime fechaPedido;
    
    @Column(name = "estado")
    @Enumerated(EnumType.STRING)
    private EstadoPedido estado;
    
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<DetallePedido> detallesPedido;
    
    @Column(name = "total", precision = 10, scale = 2)
    private BigDecimal total;

    public enum EstadoPedido {
        PENDIENTE, PROCESANDO, ENVIADO, ENTREGADO, CANCELADO
    }

    // Constructor vacío
    public Pedido() {
        this.fechaPedido = LocalDateTime.now();
        this.estado = EstadoPedido.PENDIENTE;
    }

    // Constructor con cliente
    public Pedido(Cliente cliente) {
        this();
        this.cliente = cliente;
    }

    // Método para calcular el total del pedido
    public void calcularTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (DetallePedido detalle : detallesPedido) {
            total = total.add(detalle.getSubtotal());  // Asumir que 'getSubtotal()' está implementado en DetallePedido
        }
        this.total = total;
    }

    // Getters y Setters
    public Long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Long idPedido) {
        this.idPedido = idPedido;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LocalDateTime getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(LocalDateTime fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    public List<DetallePedido> getDetallesPedido() {
        return detallesPedido;
    }

    public void setDetallesPedido(List<DetallePedido> detallesPedido) {
        this.detallesPedido = detallesPedido;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
