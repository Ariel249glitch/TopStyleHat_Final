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


import com.example.BloqueGorro.DTO.MarcaDTO;
import com.example.BloqueGorro.Model.Marca;
import com.example.BloqueGorro.Repository.MarcaRepository;

import net.datafaker.Faker;

public class MarcaApplicationTest {

@Mock
    private MarcaRepository marcaRepository;

    @InjectMocks
    private MarcaService marcaService;  
    private Faker faker = new Faker();
    @BeforeEach
    void setUp() {
		// Inicializa los componentes de simulación antes de ejecutar cada prueba
		MockitoAnnotations.openMocks(this);
	}

    //Test Buscar por id
    @Test
    void BuscarMarcaId() {
        Integer idSimulado = 44;
        String nombreSimulado = faker.options().option(
"Adidas",
            "Nike",
            "The North Face",
            "Wild Lama"
        );
        Marca marcaSimulada = new Marca();

        when(marcaRepository.findById(idSimulado)).thenReturn(Optional.of(marcaSimulada));


        MarcaDTO resultado = marcaService.buscarPorId(idSimulado);

        assertNotNull(resultado, "El resultado no debe ser nulo");
        assertEquals(idSimulado, resultado.getId(), "El ID de la marca no coincide");
        assertEquals(nombreSimulado, resultado.getNombre(), "El nombre debe coinsidir con la BD");

        //Se verifica que el servicio haya consultado al repositorio una vez

        verify(marcaRepository, times(1)).findById(idSimulado);

    }

    //Test Mostrar todas
    @Test
    void MostrarTodas(){

        //Simular marca
        Marca marca1 = new Marca();
        marca1.setId(1);
        marca1.setNombre("Adidas");
        



        Marca marca2 = new Marca();
        marca2.setId(2);
        marca2.setNombre("Nike");
        
        

        when(marcaRepository.findAll()).thenReturn(List.of(marca1, marca2));

        List<MarcaDTO> resultado = marcaService.MostrarTodas();

        //Verificar
        assertNotNull(resultado, "El resultado no debe ser nulo");
        assertEquals(2, resultado.size(), "El tamaño de la lista no coincide");
        assertEquals("Adidas", resultado.get(0).getNombre(), "El nombre de la primera Marca no coincide");
        assertEquals("Nike", resultado.get(1).getNombre(), "El nombre de la segunda Marca no coincide");

        //Verificar que el servicio haya consultado al repositorio una vez
        verify(marcaRepository, times(1)).findAll();

    }

    //Test Guardar Marca
    @Test
    void GuardarMarca(){

        Marca nuevaMarca = new Marca();
        nuevaMarca.setNombre("Adidas");

        Marca marcaGuardada = new Marca();
        marcaGuardada.setId(1);
        marcaGuardada.setNombre("Adidas");

        when(marcaRepository.save(any(Marca.class)))
                .thenReturn(marcaGuardada);

        MarcaDTO resultado = marcaService.guardarMarca(nuevaMarca);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Adidas", resultado.getNombre());

        verify(marcaRepository, times(1)).save(any(Marca.class));
    }

    //Actualizar
    @Test
    void ActualizarMarca() {

        Integer idSimulado = 1;

        Marca marcaExistente = new Marca();
        marcaExistente.setId(idSimulado);
        marcaExistente.setNombre("Adidas");

        Marca marcaActualizada = new Marca();
        marcaActualizada.setNombre("Nike");

        Marca marcaGuardada = new Marca();
        marcaGuardada.setId(idSimulado);
        marcaGuardada.setNombre("Nike");

        when(marcaRepository.findById(idSimulado))
                .thenReturn(Optional.of(marcaExistente));

        when(marcaRepository.save(any(Marca.class)))
                .thenReturn(marcaGuardada);

        Marca resultado =
                marcaService.actualizarMarca(idSimulado, marcaActualizada);

        assertNotNull(resultado);
        assertEquals("Nike", resultado.getNombre());

        verify(marcaRepository, times(1)).findById(idSimulado);
        verify(marcaRepository, times(1)).save(any(Marca.class));
    }

    //Test Eliminar
    @Test
    void EliminarMarca(){
        Integer idSimulado = 1;

        //Simular marca existente en la BD
        Marca marcaExistente = new Marca();
        marcaExistente.setId(idSimulado);
        marcaExistente.setNombre("Adidas");

        //Se encuentra la marca por id y se elimina
        when(marcaRepository.findById(idSimulado)).thenReturn(Optional.of(marcaExistente));

        String resultado = marcaService.EliminarMarca(idSimulado);

        //Verificar
        assertNotNull(resultado, "El resultado no debe ser nulo");
        

        //Verificar que se busco y elimino
        verify(marcaRepository, times(1)).findById(idSimulado);
        verify(marcaRepository, times(1)).delete(marcaExistente);
    }

}











