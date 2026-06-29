package com.example.BloqueGorro.Service;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.BloqueGorro.DTO.GorroDTO;
import com.example.BloqueGorro.Model.Gorro;
import com.example.BloqueGorro.Repository.GorroRepository;

import net.datafaker.Faker;

@ExtendWith(MockitoExtension.class)
public class GorroApplicationTest {

    @Mock
    private GorroRepository gorrorepository;

    @InjectMocks
    private GorroService gorroservice;  
    private Faker faker = new Faker();
    @BeforeEach
    void setUp() {
		// Inicializa los componentes de simulación antes de ejecutar cada prueba
		MockitoAnnotations.openMocks(this);
	}

    //Test Buscar por id
    @Test
    void BuscarGorroId() {
        Integer idSimulado = 44;
        String nombreSimulado = faker.options().option(
"New Era 9FORTY NY Yankees",
            "Nike Dri-FIT Club Cap",
            "Adidas Originals Trefoil Cap",
            "Puma Essentials Baseball Cap",
            "The North Face Salty Dog Beanie",
            "Under Armour Blitzing Cap"
        );
        String tallaSimulada = faker.options().option("S", "M", "L", "XL");
        Integer precioSimulado = faker.number().numberBetween(10000, 50000);
        Gorro GorroSimulado = new Gorro();

        when(gorrorepository.findById(idSimulado)).thenReturn(Optional.of(GorroSimulado));


        GorroDTO resultado = gorroservice.buscarPorId(idSimulado);

        assertNotNull(resultado, "El resultado no debe ser nulo");
        assertEquals(idSimulado, resultado.getId(), "El ID del gorro no coincide");
        assertEquals(nombreSimulado, resultado.getNombre(), "El nombre debe coinsidir con la BD");
        assertEquals(tallaSimulada, resultado.getTalla(), "La talla del gorro no coincide");
        assertEquals(precioSimulado, resultado.getPrecio(), "El precio del gorro no coincide");

        //Se verifica que el servicio haya consultado al repositorio una vez

        verify(gorrorepository, times(1)).findById(idSimulado);

    }

    //Test Mostrar todos
    @Test
    void MostrarTodos(){

        //Simular gorros
        Gorro gorro1 = new Gorro();
        gorro1.setId(1);
        gorro1.setNombre("Under Armour Blitzing Cap");
        gorro1.setTalla(faker.options().option("S", "M", "L", "XL"));
        gorro1.setPrecio(20000);



        Gorro gorro2 = new Gorro();
        gorro2.setId(2);
        gorro2.setNombre("New Era 9FORTY NY Yankees");
        gorro2.setTalla(faker.options().option("S", "M", "L", "XL"));
        gorro2.setPrecio(30000);
        

        when(gorrorepository.findAll()).thenReturn(List.of(gorro1, gorro2));

        List<GorroDTO> resultado = gorroservice.obtenerTodos();

        //Verificar
        assertNotNull(resultado, "El resultado no debe ser nulo");
        assertEquals(2, resultado.size(), "El tamaño de la lista no coincide");
        assertEquals("Under Armour Blitzing Cap", resultado.get(0).getNombre(), "El nombre del primer gorro no coincide");
        assertEquals("New Era 9FORTY NY Yankees", resultado.get(1).getNombre(), "El nombre del segundo gorro no coincide");

        //Verificar que el servicio haya consultado al repositorio una vez
        verify(gorrorepository, times(1)).findAll();

    }

    //Test Guardar Gorro
    @Test
    void GuardarGorro(){

        //simular gorro desde controller
        Gorro nuevoGorro = new Gorro();
        nuevoGorro.setNombre("Under Armour Blitzing Cap");
        nuevoGorro.setTalla("M");
        nuevoGorro.setPrecio(20000);

        //Simular gorro guardado en la BD
        Gorro gorroGuardado = new Gorro();
        gorroGuardado.setId(1);
        gorroGuardado.setNombre("Under Armour Blitzing Cap");
        gorroGuardado.setTalla("M");
        gorroGuardado.setPrecio(20000);

        when(gorrorepository.save(nuevoGorro)).thenReturn(gorroGuardado);

        GorroDTO resultado = gorroservice.guardarGorro(nuevoGorro);

        //verificar
        assertNotNull(resultado, "El resultado no debe ser nulo");
        assertEquals(1, resultado.getId(), "El ID del gorro guardado no coincide");
        assertEquals("Under Armour Blitzing Cap", resultado.getNombre(), "El nombre del gorro guardado no coincide");
        assertEquals("M", resultado.getTalla(), "La talla del gorro guardado no coincide");
        assertEquals(20000, resultado.getPrecio(), "El precio del gorro guardado no coincide");

        //Verificar que el servicio haya consultado al repositorio una vez
        verify(gorrorepository, times(1)).save(nuevoGorro);
    }


    //Test Actualizar
    @Test
    void ActualizarGorro(){
        Integer idSimulado = 1;

        //Simular gorro existente en la BD
        Gorro gorroExistente = new Gorro();
        gorroExistente.setId(idSimulado);
        gorroExistente.setNombre("Under Armour Blitzing Cap");
        gorroExistente.setTalla("M");
        gorroExistente.setPrecio(20000);

        //Simular gorro actualizado desde el controller
        Gorro gorroActualizado = new Gorro();
        gorroActualizado.setNombre("New Era 9FORTY NY Yankees");
        gorroActualizado.setTalla("L");
        gorroActualizado.setPrecio(30000);

        //se encuentra el gorro por id y se guarda el gorro actualizado
        when(gorrorepository.findById(idSimulado)).thenReturn(Optional.of(gorroExistente));
        when(gorrorepository.save(gorroExistente)).thenReturn(gorroExistente);

        //Llamar al método del service para actualizar el gorro
        Gorro resultado = gorroservice.ActualizarGorro(idSimulado, gorroActualizado);

        //Verificar
        assertNotNull(resultado, "El resultado no debe ser nulo");
        assertEquals("New Era 9FORTY NY Yankees", resultado.getNombre(), "El nombre del gorro actualizado no coincide");
        assertEquals("L", resultado.getTalla(), "La talla del gorro actualizado no coincide");
        assertEquals(30000, resultado.getPrecio(), "El precio del gorro actualizado no coincide");

        //Verificar que se busco y guardo
        verify(gorrorepository, times(1)).findById(idSimulado);
        verify(gorrorepository, times(1)).save(gorroExistente);
    }

    //Test Eliminar
    @Test
    void EliminarGorro(){
        Integer idSimulado = 1;

        //Simular gorro existente en la BD
        Gorro gorroExistente = new Gorro();
        gorroExistente.setId(idSimulado);
        gorroExistente.setNombre("Under Armour Blitzing Cap");
        gorroExistente.setTalla("M");
        gorroExistente.setPrecio(20000);

        //Se encuentra el gorro por id y se elimina
        when(gorrorepository.findById(idSimulado)).thenReturn(Optional.of(gorroExistente));

        String resultado = gorroservice.Eliminar(idSimulado);

        //Verificar
        assertNotNull(resultado, "El resultado no debe ser nulo");
        

        //Verificar que se busco y elimino
        verify(gorrorepository, times(1)).findById(idSimulado);
        verify(gorrorepository, times(1)).delete(gorroExistente);
    }

}
