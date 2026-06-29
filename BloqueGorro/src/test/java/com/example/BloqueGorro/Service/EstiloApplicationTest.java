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

import com.example.BloqueGorro.DTO.EstiloDTO;
import com.example.BloqueGorro.Model.Estilo;
import com.example.BloqueGorro.Repository.EstiloRepository;


import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import net.datafaker.Faker;

@ExtendWith(MockitoExtension.class)
public class EstiloApplicationTest {

    @Mock
    private EstiloRepository estiloRepository;

    @InjectMocks
    private EstiloService estiloService;  
    private Faker faker = new Faker();
    @BeforeEach
    void setUp() {
		// Inicializa los componentes de simulación antes de ejecutar cada prueba
		MockitoAnnotations.openMocks(this);
	}

    //Test Buscar por id
    @Test
    void BuscarEstiloId() {
        Integer idSimulado = 44;
        String nombreSimulado = faker.options().option(
"Urbano",
            "Casual",
            "Invernal",
            "Rustico",
            "Veraniego"
        );
        Estilo EstiloSimulado = new Estilo();

        when(estiloRepository.findById(idSimulado)).thenReturn(Optional.of(EstiloSimulado));


        EstiloDTO resultado = estiloService.buscarPorId(idSimulado);

        assertNotNull(resultado, "El resultado no debe ser nulo");
        assertEquals(idSimulado, resultado.getId(), "El ID del estilo no coincide");
        assertEquals(nombreSimulado, resultado.getNombre(), "El nombre debe coinsidir con la BD");

        //Se verifica que el servicio haya consultado al repositorio una vez

        verify(estiloRepository, times(1)).findById(idSimulado);

    }

    //Test Mostrar todos
    @Test
    void MostrarTodos(){

        //Simular estilo
        Estilo estilo1 = new Estilo();
        estilo1.setId(1);
        estilo1.setNombre("Urbano");
        



        Estilo estilo2 = new Estilo();
        estilo2.setId(2);
        estilo2.setNombre("Casual");
        
        

        when(estiloRepository.findAll()).thenReturn(List.of(estilo1, estilo2));

        List<EstiloDTO> resultado = estiloService.MostrarTodas();

        //Verificar
        assertNotNull(resultado, "El resultado no debe ser nulo");
        assertEquals(2, resultado.size(), "El tamaño de la lista no coincide");
        assertEquals("Urbano", resultado.get(0).getNombre(), "El nombre del primer estilo no coincide");
        assertEquals("Casual", resultado.get(1).getNombre(), "El nombre del segundo estilo no coincide");

        //Verificar que el servicio haya consultado al repositorio una vez
        verify(estiloRepository, times(1)).findAll();

    }

    //Test Guardar Estilo
    @Test
    void GuardarEstilo(){

        // Simular DTO recibido desde el controller
        EstiloDTO nuevoEstiloDTO = new EstiloDTO();
        nuevoEstiloDTO.setNombre("Urbano");

        // Simular entidad guardada en BD
        Estilo estiloGuardado = new Estilo();
        estiloGuardado.setId(1);
        estiloGuardado.setNombre("Urbano");

        when(estiloRepository.save(any(Estilo.class)))
                .thenReturn(estiloGuardado);

        EstiloDTO resultado = estiloService.guardarEstilo(nuevoEstiloDTO);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Urbano", resultado.getNombre());

        verify(estiloRepository, times(1)).save(any(Estilo.class));
    }


    @Test
    void ActualizarEstilo() {

        Integer idSimulado = 1;

        // Simular estilo existente en BD
        Estilo estiloExistente = new Estilo();
        estiloExistente.setId(idSimulado);
        estiloExistente.setNombre("urbano");

        // Simular DTO recibido desde el controller
        EstiloDTO estiloActualizadoDTO = new EstiloDTO();
        estiloActualizadoDTO.setNombre("casual");

        // Simular entidad actualizada
        Estilo estiloGuardado = new Estilo();
        estiloGuardado.setId(idSimulado);
        estiloGuardado.setNombre("casual");

        when(estiloRepository.findById(idSimulado))
                .thenReturn(Optional.of(estiloExistente));

        when(estiloRepository.save(any(Estilo.class)))
                .thenReturn(estiloGuardado);

        // Llamar al service
        EstiloDTO resultado = estiloService.actualizarEsti(idSimulado, estiloActualizadoDTO);

        // Verificar
        assertNotNull(resultado);
        assertEquals("casual", resultado.getNombre());

        verify(estiloRepository, times(1)).findById(idSimulado);
        verify(estiloRepository, times(1)).save(any(Estilo.class));
    }

    //Test Eliminar
    @Test
    void EliminarEstilo(){
        Integer idSimulado = 1;

        //Simular estilo existente en la BD
        Estilo estiloExistente = new Estilo();
        estiloExistente.setId(idSimulado);
        estiloExistente.setNombre("urbano");

        //Se encuentra el estilo por id y se elimina
        when(estiloRepository.findById(idSimulado)).thenReturn(Optional.of(estiloExistente));

        String resultado = estiloService.EliminarEstilo(idSimulado);

        //Verificar
        assertNotNull(resultado, "El resultado no debe ser nulo");
        

        //Verificar que se busco y elimino
        verify(estiloRepository, times(1)).findById(idSimulado);
        verify(estiloRepository, times(1)).delete(estiloExistente);
    }

}



