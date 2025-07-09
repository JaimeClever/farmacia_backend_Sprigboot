package com.SistemaFarmaceutico.elp.controller;

import com.SistemaFarmaceutico.elp.dto.DevolucionDTO;
import com.SistemaFarmaceutico.elp.entity.Devolucion;
import com.SistemaFarmaceutico.elp.service.DevolucionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/devoluciones")
public class DevolucionController {

    @Autowired
    private DevolucionService devolucionService;

    @PostMapping
    public Devolucion registrarDevolucion(@RequestBody DevolucionDTO dto) {
        return devolucionService.registrarDevolucion(dto);
    }
}