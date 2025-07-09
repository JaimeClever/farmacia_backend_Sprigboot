package com.SistemaFarmaceutico.elp.mapper;

import com.SistemaFarmaceutico.elp.dto.ProductoDTO;
import com.SistemaFarmaceutico.elp.entity.Categoria;
import com.SistemaFarmaceutico.elp.entity.Producto;

public class ProductoMapper {

    public static ProductoDTO toDTO(Producto producto) {
        if (producto == null) return null;

        ProductoDTO dto = new ProductoDTO();
        dto.setId(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());

        Double precio = producto.getPrecio();
        Double precioInicial = producto.getPrecioInicial();

        dto.setPrecio(precio != null ? precio : 0.0);
        dto.setPrecioInicial(precioInicial != null ? precioInicial : 0.0);

        if (precio != null && precioInicial != null) {
            dto.setGanancia(precio - precioInicial);
        } else {
            dto.setGanancia(0.0);
        }

        dto.setStock(producto.getStock());
        dto.setImagenBase64(producto.getImagenBase64());

        Categoria categoria = producto.getCategoria();
        if (categoria != null) {
            dto.setCategoriaId(categoria.getId());
            dto.setCategoriaNombre(categoria.getNombre());
        }

        // Mapeo de los nuevos campos de farmacia a DTO
        dto.setCodigoBarra(producto.getCodigoBarra());
        dto.setFechaVencimiento(producto.getFechaVencimiento());
        dto.setLote(producto.getLote());
        dto.setFabricante(producto.getFabricante());
        dto.setPresentacion(producto.getPresentacion());
        dto.setRequiereReceta(producto.isRequiereReceta());

        return dto;
    }

    public static Producto toEntity(ProductoDTO dto) {
        if (dto == null) return null;

        Producto producto = new Producto();
        producto.setId(dto.getId());
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setPrecioInicial(dto.getPrecioInicial());
        producto.setStock(dto.getStock());
        producto.setImagenBase64(dto.getImagenBase64());

        Long categoriaId = dto.getCategoriaId();
        if (categoriaId != null) {
            Categoria categoria = new Categoria();
            categoria.setId(categoriaId);
            producto.setCategoria(categoria);
        }

        // Mapeo de los nuevos campos de farmacia a Entity
        producto.setCodigoBarra(dto.getCodigoBarra());
        producto.setFechaVencimiento(dto.getFechaVencimiento());
        producto.setLote(dto.getLote());
        producto.setFabricante(dto.getFabricante());
        producto.setPresentacion(dto.getPresentacion());
        producto.setRequiereReceta(dto.isRequiereReceta());

        return producto;
    }
}