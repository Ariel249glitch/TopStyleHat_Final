package topstylehut.bloquecliente.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;
import topstylehut.bloquecliente.DTO.BoletaExternaDTO;
import topstylehut.bloquecliente.DTO.ClienteDTO;
import topstylehut.bloquecliente.Model.Cliente;

public class ClienteValidaciones {

    @Autowired
    private WebClient.Builder webClientBuilder;

    public BoletaExternaDTO obtenerBoleta(Integer idCliente){

        BoletaExternaDTO fallback = new BoletaExternaDTO();

        try {
            BoletaExternaDTO resultado = webClientBuilder.build()
                .get()
                .uri("http://BloqueBoleta/api/v1/boletas/buscar-por-cliente/" + idCliente)
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

    public ClienteDTO convertirADTO(Cliente cliente) {
        ClienteDTO dto = new ClienteDTO();
        dto.setId(cliente.getId());
        dto.setNombre(cliente.getNombre());
        dto.setDireccion(cliente.getDireccion());
        return dto;
        }

}
