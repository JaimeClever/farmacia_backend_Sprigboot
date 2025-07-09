package com.SistemaFarmaceutico.elp.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "devoluciones")
public class Devolucion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fecha;

    private int cantidadDevuelta;

    private String motivo;

    @ManyToOne
    @JoinColumn(name = "detalle_venta_id")
    private DetalleVenta detalleVenta;

    public Devolucion() {
        this.fecha = LocalDate.now();
    }

    // Getters y Setters
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public LocalDate getFecha() { return fecha; }

    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public int getCantidadDevuelta() { return cantidadDevuelta; }

    public void setCantidadDevuelta(int cantidadDevuelta) { this.cantidadDevuelta = cantidadDevuelta; }

    public String getMotivo() { return motivo; }

    public void setMotivo(String motivo) { this.motivo = motivo; }

    public DetalleVenta getDetalleVenta() { return detalleVenta; }

    public void setDetalleVenta(DetalleVenta detalleVenta) { this.detalleVenta = detalleVenta; }
}
