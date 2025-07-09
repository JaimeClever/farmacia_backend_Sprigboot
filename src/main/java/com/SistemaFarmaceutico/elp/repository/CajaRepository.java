package com.SistemaFarmaceutico.elp.repository;

import com.SistemaFarmaceutico.elp.entity.Caja;
import com.SistemaFarmaceutico.elp.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CajaRepository extends JpaRepository<Caja, Long> {
    Optional<Caja> findByUsuarioAndEstado(Usuario usuario, String estado);
    List<Caja> findByUsuario_Id(Long usuarioId);
}