package com.SistemaFarmaceutico.elp.repository;

import com.SistemaFarmaceutico.elp.dto.ReportePorDiaDTO;
import com.SistemaFarmaceutico.elp.dto.ReportePorProductoDTO;
import com.SistemaFarmaceutico.elp.dto.ReportePorVendedorDTO;
import com.SistemaFarmaceutico.elp.entity.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {

    @Query("SELECT new com.SistemaFarmaceutico.elp.dto.ReportePorDiaDTO(v.fecha, SUM(v.total)) " +
            "FROM Venta v WHERE v.fecha BETWEEN :desde AND :hasta GROUP BY v.fecha ORDER BY v.fecha")
    List<ReportePorDiaDTO> reportePorDia(@Param("desde") LocalDate desde, @Param("hasta") LocalDate hasta);

    @Query("SELECT new com.SistemaFarmaceutico.elp.dto.ReportePorProductoDTO(p.nombre, SUM(d.cantidad), SUM(d.subtotal)) " +
            "FROM DetalleVenta d JOIN d.producto p JOIN d.venta v " +
            "WHERE v.fecha BETWEEN :desde AND :hasta GROUP BY p.nombre ORDER BY SUM(d.subtotal) DESC")
    List<ReportePorProductoDTO> reportePorProducto(@Param("desde") LocalDate desde, @Param("hasta") LocalDate hasta);


    @Query("SELECT new com.SistemaFarmaceutico.elp.dto.ReportePorVendedorDTO(u.nombres, COUNT(v), SUM(v.total)) " +
            "FROM Venta v JOIN v.vendedor u " +
            "WHERE v.fecha BETWEEN :desde AND :hasta " +
            "GROUP BY u.nombres ORDER BY SUM(v.total) DESC")
    List<ReportePorVendedorDTO> reportePorVendedor(@Param("desde") LocalDate desde, @Param("hasta") LocalDate hasta);

}