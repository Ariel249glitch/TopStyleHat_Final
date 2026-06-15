package com.example.BloqueBoleta.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.BloqueBoleta.DTO.ClienteDTOExterno;
import com.example.BloqueBoleta.DTO.GorroDTOExterno;

@Service
public class BoletaValidaciones {

    @Autowired
    private WebClient.Builder webClientBuilder;

    public ClienteDTOExterno obtenerCliente(Integer id){

        try {

            return webClientBuilder.build()
                    .get()
                    .uri("http://cliente/api/v1/clientes/" + id)
                    .retrieve()
                    .bodyToMono(ClienteDTOExterno.class)
                    .block();

        } catch (Exception e) {

            ClienteDTOExterno dto = new ClienteDTOExterno();

            dto.setId(id);
            dto.setNombre("Cliente no disponible");

            return dto;
        }
    }

    public GorroDTOExterno obtenerGorro(Integer id){

        try {

            return webClientBuilder.build()
                    .get()
                    .uri("http://gorro/api/v1/gorros/" + id)
                    .retrieve()
                    .bodyToMono(GorroDTOExterno.class)
                    .block();

        } catch (Exception e) {

            GorroDTOExterno dto = new GorroDTOExterno();

            dto.setId(id);
            dto.setNombre("Gorro no disponible");

            return dto;
        }
    }
}