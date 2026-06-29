package topstylehut.bloquecliente.Pruebas;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import topstylehut.bloquecliente.DTO.ComunaDTO;
import topstylehut.bloquecliente.Model.Comuna;
import topstylehut.bloquecliente.Repository.ComunaRepository;
import topstylehut.bloquecliente.Service.ComunaService;

@ExtendWith(MockitoExtension.class)
public class ComunaPruebas {

    @Mock
    private ComunaRepository comunaRepository;

    @InjectMocks
    private ComunaService comunaService;

    private Comuna comuna;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        comuna = new Comuna();
        comuna.setId(1);
        comuna.setNombre("Providencia");
    }

    @Test
    void testObtenerTodas() {
        when(comunaRepository.findAll()).thenReturn(Collections.singletonList(comuna));
        List<ComunaDTO> resultado = comunaService.obtenerTodas();
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Providencia", resultado.get(0).getNombre());
    }

    @Test
    void testBuscarPorId_Exitoso() {
        when(comunaRepository.findById(1)).thenReturn(Optional.of(comuna));
        ComunaDTO resultado = comunaService.buscarPorId(1);
        assertNotNull(resultado);
        assertEquals("Providencia", resultado.getNombre());
    }

    @Test
    void testBuscarPorId_NoEncontrado() {
        when(comunaRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> comunaService.buscarPorId(1));
    }

    @Test
    void testGuardar() {
        when(comunaRepository.save(any(Comuna.class))).thenReturn(comuna);
        ComunaDTO resultado = comunaService.guardar(comuna);
        assertNotNull(resultado);
        assertEquals("Providencia", resultado.getNombre());
    }

    @Test
    void testEliminar_Exitoso() {
        when(comunaRepository.findById(1)).thenReturn(Optional.of(comuna));
        doNothing().when(comunaRepository).delete(comuna);
        String mensaje = comunaService.eliminar(1);
        assertTrue(mensaje.contains("eliminado exitosamente"));
    }
}