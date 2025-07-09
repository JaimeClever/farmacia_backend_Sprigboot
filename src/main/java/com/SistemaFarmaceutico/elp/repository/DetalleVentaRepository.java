package com.SistemaFarmaceutico.elp.repository;

import com.SistemaFarmaceutico.elp.entity.DetalleVenta; // Changed package
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Long> {
}