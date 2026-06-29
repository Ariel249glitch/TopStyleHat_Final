package com.example.BloqueGorro.Service;

import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.BloqueGorro.DTO.ColoresDTO;
import com.example.BloqueGorro.Model.Color;
import com.example.BloqueGorro.Model.Colores;
import com.example.BloqueGorro.Model.Gorro;
import com.example.BloqueGorro.Repository.ColoresRepository;


import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;



@ExtendWith(MockitoExtension.class)
public class ColoresApplicationTest {

    @Mock
    private ColoresRepository coloresRepository;

    @Mock
    private ColoresService coloresService;
    @BeforeEach
    void setUp() {
		// Inicializa los componentes de simulación antes de ejecutar cada prueba
		MockitoAnnotations.openMocks(this);
	}

    //Test Mostrar todos los colores
    @Test
    void mostrarTodosColores() {
        
        Gorro gorro = new Gorro();
        gorro.setId(1);
        gorro.setNombre("Gorro de prueba");

        Color color = new Color();
        color.setId(1);
        color.setNombre("naranja");

        Colores relacion = new Colores();
        relacion.setGorro(gorro);
        relacion.setColor(color);


        when(coloresRepository.findAll()).thenReturn(List.of(relacion));

        List<ColoresDTO> resultado = coloresService.obtenerTodas();


        //verificar

        assertNotNull(resultado, "La lista no deberia ser nula");
        assertEquals(1, resultado.size(), "La lista deberia tener 1 relación");
        assertEquals("Gorro de prueba", resultado.get(0).getNombreGorro(),"el nombre debe coinsidir");
        assertEquals("Naranja", resultado.get(0).getNombreColor(), "El nombre del color debe coincidir");

        verify(coloresRepository, times(1)).findAll();

        
    }

}
