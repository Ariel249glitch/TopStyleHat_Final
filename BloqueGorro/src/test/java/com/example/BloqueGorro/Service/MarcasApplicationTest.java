package com.example.BloqueGorro.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import com.example.BloqueGorro.DTO.MarcasDTO;
import com.example.BloqueGorro.Model.Marcas;
import com.example.BloqueGorro.Repository.MarcasRepository;


public class MarcasApplicationTest {

    @Mock
    private MarcasRepository marcasRepository;

    @InjectMocks
    private MarcasService marcasService;  
    
    @BeforeEach
    void setUp() {
		// Inicializa los componentes de simulación antes de ejecutar cada prueba
		MockitoAnnotations.openMocks(this);
	}

    //Test Buscar por id
    @Test
    void BuscarMarcasId() {
        Integer idSimulado = 44;
        Marcas marcasSimuladas = new Marcas();

        when(marcasRepository.findById(idSimulado)).thenReturn(Optional.of(marcasSimuladas));


        MarcasDTO resultado = marcasService.buscarPorId(idSimulado);

        assertNotNull(resultado, "El resultado no debe ser nulo");
        assertEquals(idSimulado, resultado.getId(), "El ID de la marca no coincide");
        

        //Se verifica que el servicio haya consultado al repositorio una vez

        verify(marcasRepository, times(1)).findById(idSimulado);

    }

    //Test Mostrar todas
    @Test
    void MostrarTodas(){

        //Simular marcas
        Marcas marcas1 = new Marcas();
        marcas1.setId(1);
        
        Marcas marcas2 = new Marcas();
        marcas2.setId(2);

        when(marcasRepository.findAll()).thenReturn(List.of(marcas1, marcas2));

        List<MarcasDTO> resultado = marcasService.MostrarTodas();

        //Verificar
        assertNotNull(resultado, "El resultado no debe ser nulo");
        assertEquals(2, resultado.size(), "El tamaño de la lista no coincide");

        //Verificar que el servicio haya consultado al repositorio una vez
        verify(marcasRepository, times(1)).findAll();

    }

    //Test Guardar marcas
    @Test
    void GuardarMarcas(){

        Marcas nuevasMarcas = new Marcas();
        

        Marcas marcasGuardadas = new Marcas();
        marcasGuardadas.setId(1);

        when(marcasRepository.save(any(Marcas.class)))
                .thenReturn(marcasGuardadas);

        MarcasDTO resultado = marcasService.guardarMarcas(nuevasMarcas);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());

        verify(marcasRepository, times(1)).save(any(Marcas.class));
    }


    //Test Eliminar
    @Test
    void EliminarMarcas() {

        Integer idSimulado = 1;

        Marcas marcasExistente = new Marcas();
        marcasExistente.setId(idSimulado);

        when(marcasRepository.findById(idSimulado))
                .thenReturn(Optional.of(marcasExistente));

        MarcasDTO resultado =
                marcasService.eliminarMarcas(idSimulado);

        assertNotNull(resultado);

        verify(marcasRepository, times(1)).findById(idSimulado);
        verify(marcasRepository, times(1)).delete(marcasExistente);
    }

}
