package com.SistemaFarmaceutico.elp.controller;

import com.SistemaFarmaceutico.elp.dto.VentaDTO;
import com.SistemaFarmaceutico.elp.dto.VentaResponseDTO;
import com.SistemaFarmaceutico.elp.dto.ReportePorDiaDTO;
import com.SistemaFarmaceutico.elp.dto.ReportePorProductoDTO;
import com.SistemaFarmaceutico.elp.dto.ReportePorVendedorDTO;
import com.SistemaFarmaceutico.elp.entity.Caja;
import com.SistemaFarmaceutico.elp.entity.Venta;
import com.SistemaFarmaceutico.elp.mapper.VentaMapper;
import com.SistemaFarmaceutico.elp.service.CajaService;
import com.SistemaFarmaceutico.elp.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @Autowired
    private CajaService cajaService;

    @GetMapping
    public List<VentaResponseDTO> listarVentas() {
        return ventaService.listarVentasConDetalles();
    }

    @PostMapping
    public ResponseEntity<VentaResponseDTO> crearVenta(@RequestBody VentaDTO dto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Venta venta = ventaService.registrarVenta(dto, username);

        Caja cajaAbierta = cajaService.obtenerCajaAbiertaPorUsuario(username);

        if (cajaAbierta != null) {
            double nuevoMontoFinal = cajaAbierta.getMontoFinal() + venta.getTotal().doubleValue();
            cajaAbierta.setMontoFinal(nuevoMontoFinal);
            cajaService.actualizarCaja(cajaAbierta);
        }

        VentaResponseDTO responseDTO = VentaMapper.toDTO(venta);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VentaResponseDTO> obtenerVentaPorId(@PathVariable Long id) {
        Venta venta = ventaService.obtenerVentaPorId(id);
        VentaResponseDTO dto = VentaMapper.toDTO(venta);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/reporte/por-dia")
    public List<ReportePorDiaDTO> obtenerReportePorDia(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {
        return ventaService.reportePorDia(desde, hasta);
    }

    @GetMapping("/reporte/por-producto")
    public List<ReportePorProductoDTO> obtenerReportePorProducto(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {
        return ventaService.reportePorProducto(desde, hasta);
    }

    @GetMapping("/reporte/por-vendedor")
    public List<ReportePorVendedorDTO> reportePorVendedor(
            @RequestParam("desde") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam("hasta") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {
        return ventaService.ReportePorVendedor(desde, hasta);
    }

}