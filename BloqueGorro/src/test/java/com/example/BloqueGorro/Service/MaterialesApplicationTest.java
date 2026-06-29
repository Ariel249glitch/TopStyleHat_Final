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


import com.example.BloqueGorro.DTO.MaterialesDTO;
import com.example.BloqueGorro.Model.Materiales;
import com.example.BloqueGorro.Repository.MaterialesRepository;

public class MaterialesApplicationTest {

    @Mock
    private MaterialesRepository materialesRepository;

    @InjectMocks
    private MaterialesService materialesService;  
    
    @BeforeEach
    void setUp() {
		// Inicializa los componentes de simulación antes de ejecutar cada prueba
		MockitoAnnotations.openMocks(this);
	}

    //Test Buscar por id
    @Test
    void BuscarMaterialesId() {
        Integer idSimulado = 23;
        
        Materiales materialesSimulado = new Materiales();

        when(materialesRepository.findById(idSimulado)).thenReturn(Optional.of(materialesSimulado));


        MaterialesDTO resultado = materialesService.buscarPorId(idSimulado);

        assertNotNull(resultado, "El resultado no debe ser nulo");
        assertEquals(idSimulado, resultado.getId(), "El ID del material no coincide");
        

        //Se verifica que el servicio haya consultado al repositorio una vez

        verify(materialesRepository, times(1)).findById(idSimulado);
    }

    //Test Mostrar todos
    @Test
    void MostrarTodos(){

        //Simular Materiales
        Materiales materiales1 = new Materiales();
        materiales1.setId(1);
        
        Materiales materiales2 = new Materiales();
        materiales2.setId(2);

        when(materialesRepository.findAll()).thenReturn(List.of(materiales1, materiales2));

        List<MaterialesDTO> resultado = materialesService.obtenerTodos();

        //Verificar
        assertNotNull(resultado, "El resultado no debe ser nulo");
        assertEquals(2, resultado.size(), "El tamaño de la lista no coincide");
        

        //Verificar que el servicio haya consultado al repositorio una vez
        verify(materialesRepository, times(1)).findAll();

    }

    //Test Guardar Materiales
    @Test
    void GuardarMateriales(){

        //simular materiales desde controller
        Materiales nuevosMateriales = new Materiales();
        

        //Simular materiales guardado en la BD
        Materiales materialesGuardados = new Materiales();
        materialesGuardados.setId(1);
        

        when(materialesRepository.save(nuevosMateriales)).thenReturn(materialesGuardados);

        MaterialesDTO resultado = materialesService.guardarMateriales(nuevosMateriales);

        //verificar
        assertNotNull(resultado, "El resultado no debe ser nulo");
        assertEquals(1, resultado.getId(), "El ID del material guardado no coincide");
        

        //Verificar que el servicio haya consultado al repositorio una vez
        verify(materialesRepository, times(1)).save(nuevosMateriales);
    }

    //Test Eliminar
    @Test
    void EliminarMateriales(){
        Integer idSimulado = 1;

        //Simular materiales existente en la BD
        Materiales materialesExistentes = new Materiales();
        materialesExistentes.setId(idSimulado);
        
        //Se encuentran los materiales por id y se elimina
        when(materialesRepository.findById(idSimulado)).thenReturn(Optional.of(materialesExistentes));

        String resultado = materialesService.eliminarMateriales(idSimulado);

        //Verificar
        assertNotNull(resultado, "El resultado no debe ser nulo");
        

        //Verificar que se busco y elimino
        verify(materialesRepository, times(1)).findById(idSimulado);
        verify(materialesRepository, times(1)).delete(materialesExistentes);
    }

}
