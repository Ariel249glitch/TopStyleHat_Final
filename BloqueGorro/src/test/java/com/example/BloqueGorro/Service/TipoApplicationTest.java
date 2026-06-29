package com.example.BloqueGorro.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

import com.example.BloqueGorro.DTO.TipoDTO;
import com.example.BloqueGorro.Model.Tipo;
import com.example.BloqueGorro.Repository.TipoRepository;

import net.datafaker.Faker;

public class TipoApplicationTest {

    @Mock
    private TipoRepository tipoRepository;

    @InjectMocks
    private TipoService tipoService;  
    private Faker faker = new Faker();
    @BeforeEach
    void setUp() {
		// Inicializa los componentes de simulación antes de ejecutar cada prueba
		MockitoAnnotations.openMocks(this);
	}

    //Test Buscar por id
    @Test
    void BuscarTipoId() {
        Integer idSimulado = 24;
        String nombreSimulado = faker.options().option(
"Jockey",
            "Boina",
            "Pescador",
            "Canotier",
            "CowBoy",
            "Gorro Lana"
        );
        Tipo TipoSimulado = new Tipo();

        when(tipoRepository.findById(idSimulado)).thenReturn(Optional.of(TipoSimulado));


        TipoDTO resultado = tipoService.buscarPorId(idSimulado);

        assertNotNull(resultado, "El resultado no debe ser nulo");
        assertEquals(idSimulado, resultado.getId(), "El ID del tipo no coincide");
        assertEquals(nombreSimulado, resultado.getNombre(), "El nombre debe coinsidir con la BD");
        

        //Se verifica que el servicio haya consultado al repositorio una vez

        verify(tipoRepository, times(1)).findById(idSimulado);

    }

    //Test Mostrar todos
    @Test
    void MostrarTodos(){

        //Simular tipo
        Tipo tipo1 = new Tipo();
        tipo1.setId(1);
        tipo1.setNombre("Boina");
        



        Tipo tipo2 = new Tipo();
        tipo2.setId(2);
        tipo2.setNombre("Canotier");
        
        

        when(tipoRepository.findAll()).thenReturn(List.of(tipo1, tipo2));

        List<TipoDTO> resultado = tipoService.MostrarTodas();

        //Verificar
        assertNotNull(resultado, "El resultado no debe ser nulo");
        assertEquals(2, resultado.size(), "El tamaño de la lista no coincide");
        assertEquals("Boina", resultado.get(0).getNombre(), "El nombre del primer tipo no coincide");
        assertEquals("Canotier", resultado.get(1).getNombre(), "El nombre del segundo tipo no coincide");

        //Verificar que el servicio haya consultado al repositorio una vez
        verify(tipoRepository, times(1)).findAll();

    }

    //Test Guardar tipo
    @Test
    void GuardarTipo(){

        //simular tipo desde controller
        Tipo nuevoTipo = new Tipo();
        nuevoTipo.setNombre("Boina");
        

        //Simular gorro guardado en la BD
        Tipo TipoGuardado = new Tipo();
        TipoGuardado.setId(1);
        TipoGuardado.setNombre("Boina");
        

        when(tipoRepository.save(nuevoTipo)).thenReturn(TipoGuardado);

        TipoDTO resultado = tipoService.guardarTipo(nuevoTipo);

        //verificar
        assertNotNull(resultado, "El resultado no debe ser nulo");
        assertEquals(1, resultado.getId(), "El ID del tipo guardado no coincide");
        assertEquals("Boina", resultado.getNombre(), "El nombre del tipo guardado no coincide");

        //Verificar que el servicio haya consultado al repositorio una vez
        verify(tipoRepository, times(1)).save(nuevoTipo);
    }


    //Test Actualizar
    @Test
    void ActualizarTipo(){
        Integer idSimulado = 1;

        //Simular Tipo existente en la BD
        Tipo TipoExistente = new Tipo();
        TipoExistente.setId(idSimulado);
        TipoExistente.setNombre("Boina");
        

        //Simular Tipo actualizado desde el controller
        Tipo tipoActualizado = new Tipo();
        tipoActualizado.setNombre("Canotier");
        

        //se encuentra el Tipo por id y se guarda el Tipo actualizado
        when(tipoRepository.findById(idSimulado)).thenReturn(Optional.of(TipoExistente));
        when(tipoRepository.save(TipoExistente)).thenReturn(TipoExistente);

        //Llamar al método del service para actualizar el tipo
        Tipo resultado = tipoService.actualizarTipo(idSimulado, tipoActualizado);

        //Verificar
        assertNotNull(resultado, "El resultado no debe ser nulo");
        assertEquals("Canotier", resultado.getNombre(), "El nombre del tipo actualizado no coincide");
        

        //Verificar que se busco y guardo
        verify(tipoRepository, times(1)).findById(idSimulado);
        verify(tipoRepository, times(1)).save(TipoExistente);
    }

    //Test Eliminar
    @Test
    void EliminarTipo(){
        Integer idSimulado = 1;

        //Simular tipo existente en la BD
        Tipo TipoExistente = new Tipo();
        TipoExistente.setId(idSimulado);
        TipoExistente.setNombre("Boina");
        

        //Se encuentra el Tipo por id y se elimina
        when(tipoRepository.findById(idSimulado)).thenReturn(Optional.of(TipoExistente));

        String resultado = tipoService.EliminarTipo(idSimulado);

        //Verificar
        assertNotNull(resultado, "El resultado no debe ser nulo");
        

        //Verificar que se busco y elimino
        verify(tipoRepository, times(1)).findById(idSimulado);
        verify(tipoRepository, times(1)).delete(TipoExistente);
    }

}
