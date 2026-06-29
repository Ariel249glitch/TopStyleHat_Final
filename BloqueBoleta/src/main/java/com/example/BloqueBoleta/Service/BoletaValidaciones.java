package com.example.BloqueBoleta.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.BloqueBoleta.DTO.ClienteDTOExterno;
import com.example.BloqueBoleta.DTO.GorroDTOExterno;

@Service
public class BoletaValidaciones {

    private static final Logger log = LoggerFactory.getLogger(BoletaValidaciones.class);

    @Autowired
    private WebClient.Builder webClientBuilder;

    public ClienteDTOExterno obtenerCliente(Integer id) {
        log.info("Solicitando cliente externo con ID: {}", id);
        try {
            ClienteDTOExterno response = webClientBuilder.build()
                    .get()
                    .uri("http://cliente/api/v1/clientes/" + id)
                    .retrieve()
                    .bodyToMono(ClienteDTOExterno.class)
                    .block();
            log.info("Cliente externo obtenido: {}", response);
            return response;
        } catch (Exception e) {
            log.warn("Error al obtener cliente externo ID {}: {}", id, e.getMessage());
            ClienteDTOExterno dto = new ClienteDTOExterno();
            dto.setId(id);
            dto.setNombre("Cliente no disponible");
            return dto;
        }
    }

    public GorroDTOExterno obtenerGorro(Integer id) {
        log.info("Solicitando gorro externo con ID: {}", id);
        try {
            GorroDTOExterno response = webClientBuilder.build()
                    .get()
                    .uri("http://gorro/api/v1/gorros/" + id)
                    .retrieve()
                    .bodyToMono(GorroDTOExterno.class)
                    .block();
            log.info("Gorro externo obtenido: {}", response);
            return response;
        } catch (Exception e) {
            log.warn("Error al obtener gorro externo ID {}: {}", id, e.getMessage());
            GorroDTOExterno dto = new GorroDTOExterno();
            dto.setId(id);
            dto.setNombre("Gorro no disponible");
            return dto;
        }
    }
}