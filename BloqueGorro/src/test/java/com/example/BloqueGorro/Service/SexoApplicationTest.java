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

import com.example.BloqueGorro.DTO.SexoDTO;
import com.example.BloqueGorro.Model.Sexo;
import com.example.BloqueGorro.Repository.SexoRepository;

import net.datafaker.Faker;

public class SexoApplicationTest {

    @Mock
    private SexoRepository sexoRepository;

    @InjectMocks
    private SexoService sexoService;

    private Faker faker = new Faker();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    //Buscar por id
    @Test
    void BuscarPorId() {
        Integer idSimulado = 1;

        String nombreAleatorio = faker.options().option(
    "Hombre",
                "Mujer",
                "Unisex"
        );

        Sexo sexoFalso = new Sexo();
        sexoFalso.setId(idSimulado);
        sexoFalso.setNombre(nombreAleatorio);

        when(sexoRepository.findById(idSimulado)).thenReturn(Optional.of(sexoFalso));

        SexoDTO resultado = sexoService.buscarPorId(idSimulado);

        assertNotNull(resultado, "El DTO resultante no deberia estar nulo");
        assertEquals(sexoFalso.getNombre(), resultado.getNombre(), "El nombre transformado al DTO debe coincidir con el de la BD");

        verify(sexoRepository, times(1)).findById(idSimulado);
    }

    //Test Guardar
    @Test
    void GuardarSexo() {
        Sexo sexoNuevo = new Sexo();
        sexoNuevo.setNombre("Unisex");

        Sexo sexoGuardado = new Sexo();
        sexoGuardado.setId(1);
        sexoGuardado.setNombre("Unisex");

        when(sexoRepository.save(sexoNuevo)).thenReturn(sexoGuardado);

        SexoDTO resultado = sexoService.guardarSexo(sexoNuevo);

        assertNotNull(resultado, "El DTO guardado no deberia ser nulo");
        assertEquals(1, resultado.getId(), "El ID debe ser el que asignó la BD");
        assertEquals("Unisex", resultado.getNombre(), "El genero debe coincidir");

        verify(sexoRepository, times(1)).save(sexoNuevo);
    }

    //Mostrar todo
    @Test
    void ObtenerTodos() {
        Sexo sexo1 = new Sexo();
        sexo1.setId(1);
        sexo1.setNombre("Hombre");

        Sexo sexo2 = new Sexo();
        sexo2.setId(2);
        sexo2.setNombre("Mujer");

        when(sexoRepository.findAll()).thenReturn(List.of(sexo1, sexo2));

        List<SexoDTO> resultado = sexoService.mostrarTodos();

        assertNotNull(resultado, "La lista no deberia ser nula");
        assertEquals(2, resultado.size(), "La lista deberia tener 2 sexos");
        assertEquals("Hombre", resultado.get(0).getNombre(), "El primer genero debe coincidir");
        assertEquals("Mujer", resultado.get(1).getNombre(), "El segundo genero debe coincidir");

        verify(sexoRepository, times(1)).findAll();
    }

    //Test Actualizar
    @Test
    void ActualizarSexo() {
        Integer idSimulado = 1;

        Sexo sexoExistente = new Sexo();
        sexoExistente.setId(idSimulado);
        sexoExistente.setNombre("Hombre");

        Sexo datosNuevos = new Sexo();
        datosNuevos.setNombre("Unisex");

        when(sexoRepository.findById(idSimulado))
                .thenReturn(Optional.of(sexoExistente));

        when(sexoRepository.save(sexoExistente))
                .thenReturn(sexoExistente);

        Sexo resultado = sexoService.actualizarSexo(idSimulado, datosNuevos);

        assertNotNull(resultado, "El sexo actualizado no deberia ser nulo");
        assertEquals("Unisex", resultado.getNombre(), "El genero deberia actualizarse");

        verify(sexoRepository, times(1)).findById(idSimulado);
        verify(sexoRepository, times(1)).save(sexoExistente);
    }

    //Test Eliminar
    @Test
    void testEliminarSexo_exitoso() {
        Integer idSimulado = 1;

        Sexo sexoExistente = new Sexo();
        sexoExistente.setId(idSimulado);
        sexoExistente.setNombre("Unisex");

        when(sexoRepository.findById(idSimulado)).thenReturn(Optional.of(sexoExistente));

        String resultado = sexoService.eliminarSexo(idSimulado);

        assertNotNull(resultado, "El mensaje no deberia ser nulo");

        verify(sexoRepository, times(1)).findById(idSimulado);
        verify(sexoRepository, times(1)).delete(sexoExistente);
    }

}
