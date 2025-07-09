package com.SistemaFarmaceutico.elp.service;

import com.SistemaFarmaceutico.elp.dto.CategoriaDTO;
import com.SistemaFarmaceutico.elp.entity.Categoria;
import com.SistemaFarmaceutico.elp.mapper.CategoriaMapper;
import com.SistemaFarmaceutico.elp.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepo;

    public CategoriaDTO guardar(CategoriaDTO dto) {
        Categoria categoria = CategoriaMapper.toEntity(dto);
        return CategoriaMapper.toDTO(categoriaRepo.save(categoria));
    }

    public List<CategoriaDTO> listarTodas() {
        return categoriaRepo.findAll().stream()
                .map(CategoriaMapper::toDTO)
                .collect(Collectors.toList());
    }

    public CategoriaDTO buscarPorId(Long id) {
        return categoriaRepo.findById(id)
                .map(CategoriaMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
    }

    public CategoriaDTO actualizar(CategoriaDTO dto) {
        Categoria categoriaExistente = categoriaRepo.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada para actualizar"));

        // Actualizamos el nombre (u otros campos si hubiera)
        categoriaExistente.setNombre(dto.getNombre());

        Categoria categoriaActualizada = categoriaRepo.save(categoriaExistente);
        return CategoriaMapper.toDTO(categoriaActualizada);
    }

}