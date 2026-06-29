package com.example.BloqueGorro.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.BloqueGorro.DTO.BoletaExternaDTO;
import com.example.BloqueGorro.DTO.GorroDTO;
import com.example.BloqueGorro.Model.Gorro;

import reactor.core.publisher.Mono;

public class GorroValidaciones {

    @Autowired
    private WebClient.Builder webClientBuilder;

    public BoletaExternaDTO obtenerBoleta(Integer idGorro){

        BoletaExternaDTO fallback = new BoletaExternaDTO();

        try {
            BoletaExternaDTO resultado = webClientBuilder.build()
                .get()
                .uri("http://BloqueBoleta/api/v1/boletas/buscar-por-gorro/" + idGorro)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                    response -> Mono.empty())
                .bodyToMono(BoletaExternaDTO.class)
                .block();

            if (resultado != null) {
                return resultado;
            }

            // fallback seguro
            fallback.setId(0);
            fallback.setFecha(null);

            return fallback;

        } catch (Exception e) {

            fallback.setId(0);
            fallback.setFecha(null);

            return fallback;
        }
    }

    public GorroDTO convertirADTO(Gorro gorro) {
        GorroDTO dto = new GorroDTO();
        dto.setId(gorro.getId());
        dto.setNombre(gorro.getNombre());
        dto.setTalla(gorro.getTalla());
        dto.setPrecio(gorro.getPrecio());
        return dto;
        }

}




