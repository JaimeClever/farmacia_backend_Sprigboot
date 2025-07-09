package com.SistemaFarmaceutico.elp.controller;

import com.SistemaFarmaceutico.elp.dto.ProductoDTO;
import com.SistemaFarmaceutico.elp.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public List<ProductoDTO> listarTodos() {
        return productoService.listarTodos();
    }

    @PostMapping
    public ProductoDTO guardar(@RequestBody ProductoDTO productoDTO) {
        return productoService.guardar(productoDTO);
    }

    @PutMapping("/{id}")
    public ProductoDTO actualizar(@PathVariable Long id, @RequestBody ProductoDTO productoDTO) {
        return productoService.actualizar(id, productoDTO);
    }

    @GetMapping("/{id}")
    public ProductoDTO buscarPorId(@PathVariable Long id) {
        return productoService.buscarPorId(id);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
    }

    @GetMapping("/buscar")
    public List<ProductoDTO> buscarPorNombreYCategoria(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) Long categoriaId) {
        return productoService.buscarPorNombreYCategoria(nombre, categoriaId);
    }

    @GetMapping("/{id}/ganancia")
    public double obtenerGananciaProducto(@PathVariable Long id) {
        return productoService.calcularGananciaPorProducto(id);
    }
}