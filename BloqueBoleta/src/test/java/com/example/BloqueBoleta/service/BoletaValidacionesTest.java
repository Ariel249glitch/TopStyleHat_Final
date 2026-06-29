package com.example.BloqueBoleta.Service;

import com.example.BloqueBoleta.DTO.ClienteDTOExterno;
import com.example.BloqueBoleta.DTO.GorroDTOExterno;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BoletaValidacionesTest {

    @Autowired
    private BoletaValidaciones boletaValidaciones;

    @MockBean
    private WebClient.Builder webClientBuilder;

    @MockBean
    private WebClient webClient;

    @MockBean
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @MockBean
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @MockBean
    private WebClient.ResponseSpec responseSpec;

    @Test
    public void testObtenerClienteExitoso() {
    
        when(webClientBuilder.build()).thenReturn(webClient);
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);

        ClienteDTOExterno clienteMock = new ClienteDTOExterno();
        clienteMock.setId(1);
        clienteMock.setNombre("Juan");
        when(responseSpec.bodyToMono(ClienteDTOExterno.class)).thenReturn(Mono.just(clienteMock));

        ClienteDTOExterno resultado = boletaValidaciones.obtenerCliente(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Juan", resultado.getNombre());
    }

    @Test
    public void testObtenerClienteFallback() {
        when(webClientBuilder.build()).thenReturn(webClient);
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(ClienteDTOExterno.class)).thenThrow(new RuntimeException("Error"));

        ClienteDTOExterno resultado = boletaValidaciones.obtenerCliente(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Cliente no disponible", resultado.getNombre());
    }

    @Test
    public void testObtenerGorroExitoso() {
        when(webClientBuilder.build()).thenReturn(webClient);
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);

        GorroDTOExterno gorroMock = new GorroDTOExterno();
        gorroMock.setId(1);
        gorroMock.setNombre("Gorro A");
        when(responseSpec.bodyToMono(GorroDTOExterno.class)).thenReturn(Mono.just(gorroMock));

        GorroDTOExterno resultado = boletaValidaciones.obtenerGorro(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Gorro A", resultado.getNombre());
    }

    @Test
    public void testObtenerGorroFallback() {
        when(webClientBuilder.build()).thenReturn(webClient);
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(GorroDTOExterno.class)).thenThrow(new RuntimeException("Error"));

        GorroDTOExterno resultado = boletaValidaciones.obtenerGorro(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Gorro no disponible", resultado.getNombre());
    }
}