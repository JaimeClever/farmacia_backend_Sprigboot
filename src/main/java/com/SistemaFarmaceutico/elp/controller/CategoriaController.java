package com.SistemaFarmaceutico.elp.controller;

// Updated imports to reflect the new package structure
import com.SistemaFarmaceutico.elp.dto.CategoriaDTO;
import com.SistemaFarmaceutico.elp.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@CrossOrigin(origins = "http://4200") // Consider if "http://localhost:4200" should be configurable or just "4200"
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService; // Updated type reference

    @PostMapping
    public CategoriaDTO guardar(@RequestBody CategoriaDTO dto) {
        return categoriaService.guardar(dto);
    }

    @GetMapping
    public List<CategoriaDTO> listarTodas() {
        return categoriaService.listarTodas();
    }

    @GetMapping("/{id}")
    public CategoriaDTO buscarPorId(@PathVariable Long id) {
        return categoriaService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public CategoriaDTO actualizar(@PathVariable Long id, @RequestBody CategoriaDTO dto) {
        dto.setId(id);
        return categoriaService.actualizar(dto);
    }


}