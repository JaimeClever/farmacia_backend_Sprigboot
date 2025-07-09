package com.SistemaFarmaceutico.elp.repository;

import com.SistemaFarmaceutico.elp.entity.Cliente; // Changed package
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByDni(String dni);
}