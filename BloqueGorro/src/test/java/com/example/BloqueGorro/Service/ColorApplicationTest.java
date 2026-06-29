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

import com.example.BloqueGorro.DTO.ColorDTO;
import com.example.BloqueGorro.Model.Color;
import com.example.BloqueGorro.Repository.ColorRepository;

import net.datafaker.Faker;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ColorApplicationTest {

    @Mock
    private ColorRepository colorRepository;

    @InjectMocks
    private ColorService colorService;
    private Faker faker = new Faker();
    @BeforeEach
    void setUp() {
		// Inicializa los componentes de simulación antes de ejecutar cada prueba
		MockitoAnnotations.openMocks(this);
	}


    //Mostrar todos

    @Test
    void MostrarTodos() {
        Color color1 = new Color();
        color1.setId(1);
        color1.setNombre("Rojo");

        Color color2 = new Color();
        color2.setId(2);
        color2.setNombre("Azul");

        when(colorRepository.findAll()).thenReturn(List.of(color1, color2));

        List<ColorDTO> resultado = colorService.obtenerTodos();

        assertNotNull(resultado, "La lista no deberia estar vacia");
        assertEquals(2, resultado.size(), "La lista deberia tener 2 colores");
        assertEquals("Rojo", resultado.get(0).getNombre(), "El primer nombre debe coincide");
        assertEquals("azul", resultado.get(1).getNombre(), "El segundo nombre no coincide");

        verify(colorRepository, times(1)).findAll();
    }

    //Test Buscar por id
    @Test
    void BuscarColorId() {
        Integer idSimulado = 1;
        String nombreSimulado = faker.options().option(
"rojo",
            "azul",
            "verde",
            "naranja",
            "negro"
        );

        Color colorFalso = new Color();
        colorFalso.setId(idSimulado);
        colorFalso.setNombre(nombreSimulado);

        when(colorRepository.findById(idSimulado)).thenReturn(Optional.of(colorFalso));

        ColorDTO resultado = colorService.buscarPorId(idSimulado);

        assertNotNull(resultado, "El DTO resultante no deberia estar nulo");
        assertEquals(colorFalso.getNombre(), resultado.getNombre(), "El nombre transformado al DTO debe coincidir con el de la BD");

        verify(colorRepository, times(1)).findById(idSimulado);
    }

    //Test Guardar

    @Test
    void testGuardarColor() {
        Color colorNuevo = new Color();
        colorNuevo.setNombre("rojo");

        Color colorGuardado = new Color();
        colorGuardado.setId(1);
        colorGuardado.setNombre("rojo");

        when(colorRepository.save(colorNuevo)).thenReturn(colorGuardado);

        ColorDTO resultado = colorService.guardar(colorNuevo);

        assertNotNull(resultado, "El DTO guardado no deberia ser nulo");
        assertEquals(1, resultado.getId(), "El ID debe ser el que asignó la BD");
        assertEquals("rojo", resultado.getNombre(), "El nombre debe coincidir");

        verify(colorRepository, times(1)).save(colorNuevo);
    }

    

    //Test Actualizar
    @Test
    void ActualizarColor(){
        Integer idSimulado = 1;

        //Simular color existente en la BD
        Color colorExistente = new Color();
        colorExistente.setId(idSimulado);
        colorExistente.setNombre("azul");
        

        //Simular color actualizado desde el controller
        Color colorActualizado = new Color();
        colorActualizado.setNombre("rojo");
        

        //se encuentra el color por id y se guarda el color actualizado
        when(colorRepository.findById(idSimulado)).thenReturn(Optional.of(colorExistente));
        when(colorRepository.save(colorExistente)).thenReturn(colorExistente);

        //Llamar al método del service para actualizar el color
        Color resultado = colorService.actualizar(idSimulado, colorActualizado);

        //Verificar
        assertNotNull(resultado, "El resultado no debe ser nulo");
        assertEquals("rojo", resultado.getNombre(), "El nombre del color actualizado no coincide");
        

        //Verificar que se busco y guardo
        verify(colorRepository, times(1)).findById(idSimulado);
        verify(colorRepository, times(1)).save(colorExistente);

    }

    //Test Eliminar
    @Test
    void EliminarGorro(){
        Integer idSimulado = 1;

        //Simular color existente en la BD
        Color colorExistente = new Color();
        colorExistente.setId(idSimulado);
        colorExistente.setNombre("azul");

        //Se encuentra el color por id y se elimina
        when(colorRepository.findById(idSimulado)).thenReturn(Optional.of(colorExistente));

        String resultado = colorService.eliminar(idSimulado);

        //Verificar
        assertNotNull(resultado, "El resultado no debe ser nulo");
        

        //Verificar que se busco y elimino
        verify(colorRepository, times(1)).findById(idSimulado);
        verify(colorRepository, times(1)).delete(colorExistente);
    }


}
