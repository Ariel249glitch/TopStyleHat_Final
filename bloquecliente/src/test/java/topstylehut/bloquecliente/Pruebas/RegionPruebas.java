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

import topstylehut.bloquecliente.DTO.RegionDTO;
import topstylehut.bloquecliente.Model.Region;
import topstylehut.bloquecliente.Repository.RegionRepository;
import topstylehut.bloquecliente.Service.Regionservice;

@ExtendWith(MockitoExtension.class)
public class RegionPruebas {

    @Mock
    private RegionRepository regionRepository;

    @InjectMocks
    private Regionservice regionService;

    private Region region;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        region = new Region();
        region.setId(1);
        region.setNombre("Metropolitana");
    }

    @Test
    void testObtenerTodas() {
        when(regionRepository.findAll()).thenReturn(Collections.singletonList(region));
        List<RegionDTO> resultado = regionService.obtenerTodas();
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Metropolitana", resultado.get(0).getNombre());
    }

    @Test
    void testBuscarPorId_Exitoso() {
        when(regionRepository.findById(1)).thenReturn(Optional.of(region));
        RegionDTO resultado = regionService.buscarPorId(1);
        assertNotNull(resultado);
        assertEquals("Metropolitana", resultado.getNombre());
    }

    @Test
    void testBuscarPorId_NoEncontrado() {
        when(regionRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> regionService.buscarPorId(1));
    }

    @Test
    void testGuardar() {
        when(regionRepository.save(any(Region.class))).thenReturn(region);
        RegionDTO resultado = regionService.guardar(region);
        assertNotNull(resultado);
        assertEquals("Metropolitana", resultado.getNombre());
    }

    @Test
    void testEliminar_Exitoso() {
        when(regionRepository.findById(1)).thenReturn(Optional.of(region));
        doNothing().when(regionRepository).delete(region);
        String mensaje = regionService.eliminar(1);
        assertTrue(mensaje.contains("eliminado exitosamente"));
    }
}