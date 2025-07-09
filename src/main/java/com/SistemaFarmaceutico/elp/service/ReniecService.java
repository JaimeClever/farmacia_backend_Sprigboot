package com.SistemaFarmaceutico.elp.service;

import com.SistemaFarmaceutico.elp.dto.ReniecResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ReniecService {

    private static final String TOKEN = "65f49f80cc312fbe8ab3d5de68614dcd5e68b2ebc84d7b2284446b6e76df8a7b";
    private static final String URL = "https://apiperu.dev/api/dni/";

    @Autowired
    private RestTemplate restTemplate;

    public ReniecResponse buscarPorDni(String dni) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + TOKEN);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<ReniecResponse> response = restTemplate.exchange(
                    URL + dni,
                    HttpMethod.GET,
                    entity,
                    ReniecResponse.class
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return response.getBody();
            } else {
                throw new RuntimeException("No se pudo obtener datos del DNI: " + dni);
            }
        } catch (Exception ex) {
            throw new RuntimeException("Error al consultar RENIEC: " + ex.getMessage(), ex);
        }
    }

}