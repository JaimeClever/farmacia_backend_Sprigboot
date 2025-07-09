package com.SistemaFarmaceutico.elp.repository;

import com.SistemaFarmaceutico.elp.entity.MovimientoCaja; // Changed package
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovimientoCajaRepository extends JpaRepository<MovimientoCaja, Long> {
    List<MovimientoCaja> findByCaja_Id(Long cajaId);
}