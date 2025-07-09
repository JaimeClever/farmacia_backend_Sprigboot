package com.SistemaFarmaceutico.elp.service;

import com.SistemaFarmaceutico.elp.dto.DevolucionDTO;
import com.SistemaFarmaceutico.elp.entity.Devolucion;
import com.SistemaFarmaceutico.elp.entity.DetalleVenta;
import com.SistemaFarmaceutico.elp.entity.Producto;
import com.SistemaFarmaceutico.elp.repository.DevolucionRepository;
import com.SistemaFarmaceutico.elp.repository.DetalleVentaRepository;
import com.SistemaFarmaceutico.elp.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class DevolucionService {

    @Autowired
    private DevolucionRepository devolucionRepository;

    @Autowired
    private DetalleVentaRepository detalleVentaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    public Devolucion registrarDevolucion(DevolucionDTO dto) {
        DetalleVenta detalleVenta = detalleVentaRepository.findById(dto.getDetalleVentaId())
                .orElseThrow(() -> new RuntimeException("Detalle de venta no encontrado"));

        if (dto.getCantidadDevuelta() > detalleVenta.getCantidad()) {
            throw new RuntimeException("Cantidad devuelta mayor a la vendida");
        }

        Producto producto = detalleVenta.getProducto();
        producto.setStock(producto.getStock() + dto.getCantidadDevuelta());
        productoRepository.save(producto);

        Devolucion devolucion = new Devolucion();
        devolucion.setCantidadDevuelta(dto.getCantidadDevuelta());
        devolucion.setMotivo(dto.getMotivo());
        devolucion.setDetalleVenta(detalleVenta);
        devolucion.setFecha(LocalDate.now());

        return devolucionRepository.save(devolucion);
    }
}