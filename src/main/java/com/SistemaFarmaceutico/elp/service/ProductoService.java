package com.SistemaFarmaceutico.elp.service;

import com.SistemaFarmaceutico.elp.dto.ProductoDTO;
import com.SistemaFarmaceutico.elp.entity.Categoria;
import com.SistemaFarmaceutico.elp.entity.Producto;
import com.SistemaFarmaceutico.elp.mapper.ProductoMapper;
import com.SistemaFarmaceutico.elp.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<ProductoDTO> listarTodos() {
        return productoRepository.findAll()
                .stream()
                .map(ProductoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ProductoDTO guardar(ProductoDTO dto) {
        Producto producto = ProductoMapper.toEntity(dto);
        return ProductoMapper.toDTO(productoRepository.save(producto));
    }

    public ProductoDTO actualizar(Long id, ProductoDTO dto) {
        Producto existente = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        existente.setNombre(dto.getNombre());
        existente.setDescripcion(dto.getDescripcion());
        existente.setPrecio(dto.getPrecio());
        existente.setPrecioInicial(dto.getPrecioInicial());
        existente.setStock(dto.getStock());
        existente.setImagenBase64(dto.getImagenBase64());

        // Actualizar los nuevos campos de farmacia
        existente.setCodigoBarra(dto.getCodigoBarra());
        existente.setFechaVencimiento(dto.getFechaVencimiento());
        existente.setLote(dto.getLote());
        existente.setFabricante(dto.getFabricante());
        existente.setPresentacion(dto.getPresentacion());
        existente.setRequiereReceta(dto.isRequiereReceta());


        if (dto.getCategoriaId() != null) {
            Categoria categoria = new Categoria();
            categoria.setId(dto.getCategoriaId());
            existente.setCategoria(categoria);
        }

        return ProductoMapper.toDTO(productoRepository.save(existente));
    }

    public ProductoDTO buscarPorId(Long id) {
        return productoRepository.findById(id)
                .map(ProductoMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    public void eliminar(Long id) {
        productoRepository.deleteById(id);
    }

    public List<ProductoDTO> buscarPorNombreYCategoria(String nombre, Long categoriaId) {
        return productoRepository.buscarPorNombreYCategoria(nombre, categoriaId)
                .stream()
                .map(ProductoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public double calcularGananciaPorProducto(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return producto.getPrecio() - producto.getPrecioInicial();
    }
}