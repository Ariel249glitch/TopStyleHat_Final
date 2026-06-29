package com.example.BloqueGorro.Service;

import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import com.example.BloqueGorro.DTO.MaterialDTO;
import com.example.BloqueGorro.Model.Material;
import com.example.BloqueGorro.Repository.MaterialRepository;

import net.datafaker.Faker;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MaterialApplicationTest {

    @Mock
    private MaterialRepository materialRepository;

    @InjectMocks
    private MaterialService materialService;  
    private Faker faker = new Faker();
    @BeforeEach
    void setUp() {
		// Inicializa los componentes de simulación antes de ejecutar cada prueba
		MockitoAnnotations.openMocks(this);
	}

    //Test Buscar por id
    @Test
    void BuscarMaterialId() {
        Integer idSimulado = 5;
        String nombreSimulado = faker.options().option(
"Lana",
            "Poliester",
            "Algodon",
            "Nylon",
            "Lona",
            "Polar"
        );

        Material MaterialSimulado = new Material();

        when(materialRepository.findById(idSimulado)).thenReturn(Optional.of(MaterialSimulado));


        MaterialDTO resultado = materialService.buscarPorId(idSimulado);

        assertNotNull(resultado, "El resultado no debe ser nulo");
        assertEquals(idSimulado, resultado.getId(), "El ID del material no coincide");
        assertEquals(nombreSimulado, resultado.getNombre(), "El nombre debe coinsidir con la BD");

        //Se verifica que el servicio haya consultado al repositorio una vez

        verify(materialRepository, times(1)).findById(idSimulado);

    }

    //Test Mostrar todos
    @Test
    void MostrarTodos(){

        //Simular materiales
        Material material1 = new Material();
        material1.setId(1);
        material1.setNombre("Polar");
        



        Material material2 = new Material();
        material2.setId(2);
        material2.setNombre("Lana");
        
        

        when(materialRepository.findAll()).thenReturn(List.of(material1, material2));

        List<MaterialDTO> resultado = materialService.mostrarTodos();

        //Verificar
        assertNotNull(resultado, "El resultado no debe ser nulo");
        assertEquals(2, resultado.size(), "El tamaño de la lista no coincide");
        assertEquals("Polar", resultado.get(0).getNombre(), "El nombre del primer material no coincide");
        assertEquals("Lana", resultado.get(1).getNombre(), "El nombre del segundo material no coincide");

        //Verificar que el servicio haya consultado al repositorio una vez
        verify(materialRepository, times(1)).findAll();

    }

    //Test Guardar Material
    @Test
    void GuardarMaterial(){

        //simular material desde controller
        Material nuevoMaterial = new Material();
        nuevoMaterial.setNombre("Lana");

        //Simular material guardado en la BD
        Material materialGuardado = new Material();
        materialGuardado.setId(1);
        materialGuardado.setNombre("Lana");
        

        when(materialRepository.save(nuevoMaterial)).thenReturn(materialGuardado);

        MaterialDTO resultado = materialService.guardarMaterial(nuevoMaterial);

        //verificar
        assertNotNull(resultado, "El resultado no debe ser nulo");
        assertEquals(1, resultado.getId(), "El ID del material guardado no coincide");
        assertEquals("Lana", resultado.getNombre(), "El nombre del material guardado no coincide");
        

        //Verificar que el servicio haya consultado al repositorio una vez
        verify(materialRepository, times(1)).save(nuevoMaterial);
    }


    //Test Actualizar
    @Test
    void ActualizarMaterial(){
        Integer idSimulado = 1;

        //Simular material existente en la BD
        Material materialExistente = new Material();
        materialExistente.setId(idSimulado);
        materialExistente.setNombre("Lana");
        

        //Simular material actualizado desde el controller
        Material materialActualizado = new Material();
        materialActualizado.setNombre("Polar");
        

        //se encuentra el material por id y se guarda el material actualizado
        when(materialRepository.findById(idSimulado)).thenReturn(Optional.of(materialExistente));
        when(materialRepository.save(materialExistente)).thenReturn(materialExistente);

        //Llamar al método del service para actualizar el Material
        Material resultado = materialService.actualizarMaterial(idSimulado, materialActualizado);

        //Verificar
        assertNotNull(resultado, "El resultado no debe ser nulo");
        assertEquals("Polar", resultado.getNombre(), "El nombre del material actualizado no coincide");
        

        //Verificar que se busco y guardo
        verify(materialRepository, times(1)).findById(idSimulado);
        verify(materialRepository, times(1)).save(materialExistente);
    }

    //Test Eliminar
    @Test
    void EliminarMaterial(){
        Integer idSimulado = 1;

        //Simular material existente en la BD
        Material materialExistente = new Material();
        materialExistente.setId(idSimulado);
        materialExistente.setNombre("Lana");
        

        //Se encuentra el material por id y se elimina
        when(materialRepository.findById(idSimulado)).thenReturn(Optional.of(materialExistente));

        String resultado = materialService.eliminarMaterial(idSimulado);

        //Verificar
        assertNotNull(resultado, "El resultado no debe ser nulo");
        

        //Verificar que se busco y elimino
        verify(materialRepository, times(1)).findById(idSimulado);
        verify(materialRepository, times(1)).delete(materialExistente);
    }

}
