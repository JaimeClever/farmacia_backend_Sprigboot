package com.SistemaFarmaceutico.elp.repository;

import com.SistemaFarmaceutico.elp.entity.Devolucion; // Changed package
import org.springframework.data.jpa.repository.JpaRepository;

public interface DevolucionRepository extends JpaRepository<Devolucion, Long> {
}