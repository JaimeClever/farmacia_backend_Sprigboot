package com.SistemaFarmaceutico.elp.service;

import com.SistemaFarmaceutico.elp.entity.Caja;
import com.SistemaFarmaceutico.elp.entity.MovimientoCaja;
import com.SistemaFarmaceutico.elp.entity.Usuario;
import com.SistemaFarmaceutico.elp.repository.CajaRepository;
import com.SistemaFarmaceutico.elp.repository.MovimientoCajaRepository;
import com.SistemaFarmaceutico.elp.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class CajaService {

    @Autowired private CajaRepository cajaRepo;
    @Autowired private MovimientoCajaRepository movimientoRepo;
    @Autowired private UsuarioRepository usuarioRepo;

    public Caja abrirCaja(Long usuarioId, double montoInicial) {
        Usuario usuario = usuarioRepo.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        cajaRepo.findByUsuarioAndEstado(usuario, "ABIERTA").ifPresent(c -> {
            throw new RuntimeException("Caja ya está abierta para este usuario");
        });

        Caja caja = new Caja();
        caja.setFecha(LocalDate.now());
        caja.setHoraApertura(LocalTime.now());
        caja.setMontoInicial(montoInicial);
        caja.setMontoFinal(montoInicial);
        caja.setEstado("ABIERTA");
        caja.setUsuario(usuario);
        return cajaRepo.save(caja);
    }

    public Caja cerrarCaja(Long usuarioId) {
        Usuario usuario = usuarioRepo.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Caja caja = cajaRepo.findByUsuarioAndEstado(usuario, "ABIERTA")
                .orElseThrow(() -> new RuntimeException("No hay caja abierta"));

        caja.setHoraCierre(LocalTime.now());

        double totalMov = caja.getMovimientos().stream()
                .mapToDouble(m -> m.getTipo().equals("DEVOLUCION") ? -m.getMonto() : m.getMonto())
                .sum();
        caja.setMontoFinal(caja.getMontoInicial() + totalMov);

        caja.setEstado("CERRADA");
        return cajaRepo.save(caja);
    }

    public Caja getCajaAbierta(Long usuarioId) {
        Usuario usuario = usuarioRepo.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return cajaRepo.findByUsuarioAndEstado(usuario, "ABIERTA")
                .orElseThrow(() -> new RuntimeException("Caja no abierta"));
    }

    public Caja obtenerCajaAbiertaPorUsuario(String username) {
        Usuario usuario = usuarioRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return cajaRepo.findByUsuarioAndEstado(usuario, "ABIERTA")
                .orElse(null);
    }

    public Caja actualizarCaja(Caja caja) {
        return cajaRepo.save(caja);
    }

    public MovimientoCaja registrarMovimiento(Long cajaId, String tipo, double monto, String descripcion) {
        Caja caja = cajaRepo.findById(cajaId)
                .orElseThrow(() -> new RuntimeException("Caja no encontrada"));

        MovimientoCaja mov = new MovimientoCaja();
        mov.setCaja(caja);
        mov.setTipo(tipo);
        mov.setMonto(monto);
        mov.setDescripcion(descripcion);
        mov.setFecha(LocalDateTime.now());
        movimientoRepo.save(mov);

        double totalMov = caja.getMovimientos().stream()
                .mapToDouble(m -> m.getTipo().equals("DEVOLUCION") ? -m.getMonto() : m.getMonto())
                .sum();
        caja.setMontoFinal(caja.getMontoInicial() + totalMov);
        cajaRepo.save(caja);

        return mov;
    }

    public List<MovimientoCaja> listarMovimientos(Long cajaId) {
        return movimientoRepo.findByCaja_Id(cajaId);
    }
}