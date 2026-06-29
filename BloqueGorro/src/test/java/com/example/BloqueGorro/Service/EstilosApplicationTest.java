package com.example.BloqueGorro.Service;

import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;


import com.example.BloqueGorro.DTO.EstilosDTO;
import com.example.BloqueGorro.Model.Estilos;
import com.example.BloqueGorro.Repository.EstilosRepository;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)

public class EstilosApplicationTest {


    @Mock
    private EstilosRepository estilosRepository;

    @InjectMocks
    private EstilosService estilosService;  
    @BeforeEach
    void setUp() {
		// Inicializa los componentes de simulación antes de ejecutar cada prueba
		MockitoAnnotations.openMocks(this);
	}


    //Test Mostrar todos
    @Test
    void MostrarTodos(){

        //Simular estilos
        Estilos estilos1 = new Estilos();
        estilos1.setId(1);

        Estilos estilos2 = new Estilos();
        estilos2.setId(2);
        ;

        when(estilosRepository.findAll()).thenReturn(List.of(estilos1, estilos2));

        List<EstilosDTO> resultado = estilosService.MostrarTodas();

        //Verificar
        assertNotNull(resultado, "El resultado no debe ser nulo");
        assertEquals(2, resultado.size(), "El tamaño de la lista no coincide");


        //Verificar que el servicio haya consultado al repositorio una vez
        verify(estilosRepository, times(1)).findAll();

    }

    //Test Eliminar
    @Test
    void EliminarEstilos(){
        Integer idSimulado = 1;

        //Simular estilo existente en la BD
        Estilos estilosExistente = new Estilos();
        estilosExistente.setId(idSimulado);
        

        //Se encuentra el estilo por id y se elimina
        when(estilosRepository.findById(idSimulado)).thenReturn(Optional.of(estilosExistente));

        String resultado = estilosService.EliminarEstilos(idSimulado);

        //Verificar
        assertNotNull(resultado, "El resultado no debe ser nulo");
        

        //Verificar que se busco y elimino
        verify(estilosRepository, times(1)).findById(idSimulado);
        verify(estilosRepository, times(1)).delete(estilosExistente);
    }


}


