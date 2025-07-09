package com.SistemaFarmaceutico.elp.controller;

import com.SistemaFarmaceutico.elp.dto.ReniecResponse;
import com.SistemaFarmaceutico.elp.service.ReniecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reniec")
public class ReniecController {

    @Autowired
    private ReniecService reniecService;

    @GetMapping("/dni/{dni}")
    public ReniecResponse buscarDni(@PathVariable String dni) {
        return reniecService.buscarPorDni(dni);
    }
}