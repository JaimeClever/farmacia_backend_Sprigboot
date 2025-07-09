package com.SistemaFarmaceutico.elp.dto;

public class ReportePorVendedorDTO {
    private String vendedor;
    private Long totalVentas;
    private Double totalMonto;

    public ReportePorVendedorDTO(String vendedor, Long totalVentas, Double totalMonto) {
        this.vendedor = vendedor;
        this.totalVentas = totalVentas;
        this.totalMonto = totalMonto;
    }

    public String getVendedor() {
        return vendedor;
    }

    public Long getTotalVentas() {
        return totalVentas;
    }

    public Double getTotalMonto() {
        return totalMonto;
    }
}
