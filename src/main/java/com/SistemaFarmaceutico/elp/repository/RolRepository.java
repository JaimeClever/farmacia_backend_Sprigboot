package com.SistemaFarmaceutico.elp.repository;

import com.SistemaFarmaceutico.elp.entity.Rol; // Changed package
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByNombre(Rol.NombreRol nombre);
}