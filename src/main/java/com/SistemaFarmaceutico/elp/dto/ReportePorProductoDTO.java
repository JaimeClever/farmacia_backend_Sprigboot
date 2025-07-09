package com.SistemaFarmaceutico.elp.dto;

public class ReportePorProductoDTO {
    private String producto;
    private Long cantidadTotal;
    private Double totalVendido;

    public ReportePorProductoDTO(String producto, Long cantidadTotal, Double totalVendido) {
        this.producto = producto;
        this.cantidadTotal = cantidadTotal;
        this.totalVendido = totalVendido;
    }

    public String getProducto() {
        return producto;
    }

    public Long getCantidadTotal() {
        return cantidadTotal;
    }

    public Double getTotalVendido() {
        return totalVendido;
    }
}
