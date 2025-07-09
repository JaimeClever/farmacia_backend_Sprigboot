package com.SistemaFarmaceutico.elp.controller;

// Updated imports to reflect the new package structure
import com.SistemaFarmaceutico.elp.entity.Caja;
import com.SistemaFarmaceutico.elp.entity.MovimientoCaja;
import com.SistemaFarmaceutico.elp.entity.Usuario;
import com.SistemaFarmaceutico.elp.repository.UsuarioRepository;
import com.SistemaFarmaceutico.elp.security.JwtProvider;
import com.SistemaFarmaceutico.elp.service.CajaService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/caja")
public class CajaController {

    @Autowired
    private CajaService cajaService;
    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UsuarioRepository usuarioRepository;


    private Long obtenerUsuarioIdDesdeToken(HttpServletRequest request) {
        String token = jwtProvider.getTokenFromRequest(request);
        String username = jwtProvider.getUsernameFromToken(token);
        Usuario usuario = usuarioRepository.findByUsername(username).orElseThrow();
        return usuario.getId();
    }

    @PostMapping("/abrir")
    public Caja abrirCaja(@RequestParam double montoInicial, HttpServletRequest request) {
        Long usuarioId = obtenerUsuarioIdDesdeToken(request);
        return cajaService.abrirCaja(usuarioId, montoInicial);
    }

    @PostMapping("/cerrar")
    public Caja cerrarCaja(HttpServletRequest request) {
        Long usuarioId = obtenerUsuarioIdDesdeToken(request);
        return cajaService.cerrarCaja(usuarioId);
    }

    @GetMapping("/actual")
    public Caja obtenerCajaActual(HttpServletRequest request) {
        Long usuarioId = obtenerUsuarioIdDesdeToken(request);
        return cajaService.getCajaAbierta(usuarioId);
    }

    @PostMapping("/{id}/movimientos")
    public MovimientoCaja agregarMovimiento(
            @PathVariable Long id,
            @RequestParam String tipo,
            @RequestParam double monto,
            @RequestParam String descripcion
    ) {
        return cajaService.registrarMovimiento(id, tipo, monto, descripcion);
    }

    @GetMapping("/{id}/movimientos")
    public List<MovimientoCaja> listarMovimientos(@PathVariable Long id) {
        return cajaService.listarMovimientos(id);
    }
}