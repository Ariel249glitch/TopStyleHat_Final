package com.example.BloqueGorro.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.BloqueGorro.DTO.GorrosDTO;
import com.example.BloqueGorro.Model.Gorros;
import com.example.BloqueGorro.Repository.GorrosRepository;



public class GorrosApplicationTest {

    @Mock
    private GorrosRepository gorrosRepository;

    @InjectMocks
    private GorrosService gorrosService;  
    @BeforeEach
    void setUp() {
		// Inicializa los componentes de simulación antes de ejecutar cada prueba
		MockitoAnnotations.openMocks(this);
	}

    //Test Mostrar todos
    @Test
    void MostrarTodos(){

        //Simular gorros
        Gorros gorros1 = new Gorros();
        gorros1.setId(1);



        Gorros gorros2 = new Gorros();
        gorros2.setId(2);
        

        when(gorrosRepository.findAll()).thenReturn(List.of(gorros1, gorros2));

        List<GorrosDTO> resultado = gorrosService.obtenerTodos();

        //Verificar
        assertNotNull(resultado, "El resultado no debe ser nulo");
        assertEquals(2, resultado.size(), "El tamaño de la lista no coincide");

        //Verificar que el servicio haya consultado al repositorio una vez
        verify(gorrosRepository, times(1)).findAll();

    }

    

}
