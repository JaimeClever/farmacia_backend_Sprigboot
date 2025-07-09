package com.SistemaFarmaceutico.elp.dto;

import java.time.LocalDate;

public class ReportePorDiaDTO {
    private LocalDate fecha;
    private Double total;

    public ReportePorDiaDTO(LocalDate fecha, Double total) {
        this.fecha = fecha;
        this.total = total;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public Double getTotal() {
        return total;
    }
}
