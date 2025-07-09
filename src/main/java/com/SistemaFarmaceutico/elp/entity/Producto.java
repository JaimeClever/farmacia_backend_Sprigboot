package com.SistemaFarmaceutico.elp.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDate; // Import for LocalDate
import java.util.List;

@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;
    private double precio = 0.0;
    private Double precioInicial = 0.0;
    private int stock;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String imagenBase64;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
    @JsonManagedReference("producto-detalle")
    private List<DetalleVenta> detalles;

    // Nuevos campos para farmacia
    @Column(unique = true)
    private String codigoBarra; // Código de barras del producto

    private LocalDate fechaVencimiento; // Fecha de vencimiento

    private String lote; // Número de lote

    private String fabricante; // Fabricante del producto

    private String presentacion; // Forma de presentación (ej: tableta, jarabe, crema)

    private boolean requiereReceta; // Si el producto requiere receta médica

    public Producto() {
    }

    public Producto(Long id, String nombre, String descripcion, double precio, Double precioInicial, int stock, String imagenBase64, Categoria categoria, List<DetalleVenta> detalles, String codigoBarra, LocalDate fechaVencimiento, String lote, String fabricante, String presentacion, boolean requiereReceta) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.precioInicial = precioInicial;
        this.stock = stock;
        this.imagenBase64 = imagenBase64;
        this.categoria = categoria;
        this.detalles = detalles;
        this.codigoBarra = codigoBarra;
        this.fechaVencimiento = fechaVencimiento;
        this.lote = lote;
        this.fabricante = fabricante;
        this.presentacion = presentacion;
        this.requiereReceta = requiereReceta;
    }

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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Double getPrecioInicial() {
        return precioInicial;
    }

    public void setPrecioInicial(Double precioInicial) {
        this.precioInicial = precioInicial;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImagenBase64() {
        return imagenBase64;
    }

    public void setImagenBase64(String imagenBase64) {
        this.imagenBase64 = imagenBase64;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public List<DetalleVenta> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleVenta> detalles) {
        this.detalles = detalles;
    }

    // Getters y Setters para los nuevos campos
    public String getCodigoBarra() {
        return codigoBarra;
    }

    public void setCodigoBarra(String codigoBarra) {
        this.codigoBarra = codigoBarra;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    public boolean isRequiereReceta() {
        return requiereReceta;
    }

    public void setRequiereReceta(boolean requiereReceta) {
        this.requiereReceta = requiereReceta;
    }
}