package com.SistemaFarmaceutico.elp.controller;


import com.SistemaFarmaceutico.elp.dto.LoginDTO;
import com.SistemaFarmaceutico.elp.dto.LoginResponse;
import com.SistemaFarmaceutico.elp.entity.Usuario;
import com.SistemaFarmaceutico.elp.repository.UsuarioRepository;
import com.SistemaFarmaceutico.elp.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO dto) {
        try {
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword())
            );

            Usuario usuario = usuarioRepository.findByUsername(dto.getUsername()).orElseThrow();
            String token = jwtProvider.generarToken(auth);

            return ResponseEntity.ok(new LoginResponse(
                    usuario.getId(), // 👈 AGREGADO
                    usuario.getUsername(),
                    usuario.getNombres(),
                    usuario.getRoles().stream()
                            .map(r -> Map.of("nombre", r.getNombre().name()))
                            .toList(),
                    token
            ));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
        }
    }
}