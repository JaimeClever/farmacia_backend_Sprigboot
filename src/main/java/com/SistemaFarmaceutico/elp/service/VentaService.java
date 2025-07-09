package com.SistemaFarmaceutico.elp.service;

import com.SistemaFarmaceutico.elp.dto.*;
import com.SistemaFarmaceutico.elp.entity.*;
import com.SistemaFarmaceutico.elp.mapper.VentaMapper;
import com.SistemaFarmaceutico.elp.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ReniecService reniecService;


    @Transactional
    public Venta registrarVenta(VentaDTO dto, String usernameAutenticado) {
        Cliente cliente = null;

        if (dto.getClienteId() != null) {
            cliente = clienteRepository.findById(dto.getClienteId())
                    .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + dto.getClienteId()));
        } else if (dto.isUsarDni()) {
            cliente = clienteRepository.findByDni(dto.getDniCliente()).orElse(null);
            if (cliente == null) {
                var reniec = reniecService.buscarPorDni(dto.getDniCliente());
                cliente = new Cliente();
                cliente.setDni(dto.getDniCliente());
                cliente.setNombres(reniec.getData().getNombreCompleto());
                cliente.setCorreo("sincorreo@cliente.pe");
                cliente = clienteRepository.save(cliente);
            }
        } else {
            cliente = new Cliente();
            cliente.setNombres(dto.getNombresCliente());
            cliente.setCorreo("boleta@cliente.pe");
            cliente.setDni("00000000");
            cliente = clienteRepository.save(cliente);
        }

        Usuario vendedor = usuarioRepository.findByUsername(usernameAutenticado)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Venta venta = new Venta();
        venta.setFecha(LocalDate.now());
        venta.setCliente(cliente);
        venta.setVendedor(vendedor);

        List<DetalleVenta> detalles = new ArrayList<>();
        double total = 0.0;

        for (VentaDTO.ItemVentaDTO item : dto.getItems()) {
            Producto producto = productoRepository.findById(item.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            if (producto.getStock() < item.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para: " + producto.getNombre());
            }

            producto.setStock(producto.getStock() - item.getCantidad());
            productoRepository.save(producto);

            DetalleVenta detalle = new DetalleVenta();
            detalle.setProducto(producto);
            detalle.setCantidad(item.getCantidad());
            detalle.setPrecio(producto.getPrecio());
            detalle.setSubtotal(producto.getPrecio() * item.getCantidad());
            detalle.setVenta(venta);

            detalles.add(detalle);
            total += detalle.getSubtotal();
        }

        venta.setTotal(total);
        venta.setDetalles(detalles);

        return ventaRepository.save(venta);
    }


    public List<VentaResponseDTO> listarVentasConDetalles() {
        List<Venta> ventas = ventaRepository.findAll();
        return VentaMapper.toDTOList(ventas);
    }


    public Venta obtenerVentaPorId(Long id) {
        return ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada con id: " + id));
    }


    public List<ReportePorDiaDTO> reportePorDia(LocalDate desde, LocalDate hasta) {
        return ventaRepository.reportePorDia(desde, hasta);
    }

    public List<ReportePorProductoDTO> reportePorProducto(LocalDate desde, LocalDate hasta) {
        return ventaRepository.reportePorProducto(desde, hasta);
    }

    public List<ReportePorVendedorDTO> ReportePorVendedor(LocalDate desde, LocalDate hasta) {
        return ventaRepository.reportePorVendedor(desde, hasta);
    }
}