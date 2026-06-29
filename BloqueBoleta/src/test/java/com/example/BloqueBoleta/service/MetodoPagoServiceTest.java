package com.example.BloqueBoleta.Service;

import com.example.BloqueBoleta.Model.MetodoPago;
import com.example.BloqueBoleta.Repository.MetodoPagoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class MetodoPagoServiceTest {

    @Autowired
    private MetodoPagoService metodoPagoService;

    @MockBean
    private MetodoPagoRepository metodoPagoRepository;

    @Test
    public void testObtenerTodos() {
        MetodoPago mp1 = new MetodoPago();
        mp1.setId(1);
        mp1.setNombre("Efectivo");
        MetodoPago mp2 = new MetodoPago();
        mp2.setId(2);
        mp2.setNombre("Tarjeta");
        when(metodoPagoRepository.findAll()).thenReturn(Arrays.asList(mp1, mp2));

        List<MetodoPagoDTO> resultados = metodoPagoService.obtenerTodos();

        assertNotNull(resultados);
        assertEquals(2, resultados.size());
        assertEquals("Efectivo", resultados.get(0).getNombre());
        verify(metodoPagoRepository, times(1)).findAll();
    }

    @Test
    public void testBuscarPorIdExistente() {
        MetodoPago mp = new MetodoPago();
        mp.setId(1);
        mp.setNombre("Efectivo");
        when(metodoPagoRepository.findById(1)).thenReturn(Optional.of(mp));

        MetodoPagoDTO dto = metodoPagoService.buscarPorId(1);

        assertNotNull(dto);
        assertEquals(1, dto.getId());
        assertEquals("Efectivo", dto.getNombre());
        verify(metodoPagoRepository, times(1)).findById(1);
    }

    @Test
    public void testBuscarPorIdNoExistente() {
        when(metodoPagoRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> metodoPagoService.buscarPorId(99));
        verify(metodoPagoRepository, times(1)).findById(99);
    }

    @Test
    public void testGuardar() {
        MetodoPago mp = new MetodoPago();
        mp.setNombre("Transferencia");
        MetodoPago mpGuardado = new MetodoPago();
        mpGuardado.setId(3);
        mpGuardado.setNombre("Transferencia");
        when(metodoPagoRepository.save(any(MetodoPago.class))).thenReturn(mpGuardado);

        MetodoPagoDTO dto = metodoPagoService.guardar(mp);

        assertNotNull(dto);
        assertEquals(3, dto.getId());
        assertEquals("Transferencia", dto.getNombre());
        verify(metodoPagoRepository, times(1)).save(mp);
    }

    @Test
    public void testEliminarExistente() {
        MetodoPago mp = new MetodoPago();
        mp.setId(1);
        mp.setNombre("Efectivo");
        when(metodoPagoRepository.findById(1)).thenReturn(Optional.of(mp));
        doNothing().when(metodoPagoRepository).delete(mp);

        String mensaje = metodoPagoService.eliminar(1);

        assertEquals("El método de pago 'Efectivo' ha sido eliminado exitosamente", mensaje);
        verify(metodoPagoRepository, times(1)).findById(1);
        verify(metodoPagoRepository, times(1)).delete(mp);
    }
}