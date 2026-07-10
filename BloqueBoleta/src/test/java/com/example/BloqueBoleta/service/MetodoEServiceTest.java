package com.example.BloqueBoleta.service;

import com.example.BloqueBoleta.DTO.MetodoEDTO;
import com.example.BloqueBoleta.Model.MetodoE;
import com.example.BloqueBoleta.Repository.MetodoERepository;
import com.example.BloqueBoleta.Service.MetodoEService;

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
public class MetodoEServiceTest {

    @Autowired
    private MetodoEService metodoEService;

    @MockBean
    private MetodoERepository metodoERepository;

    @Test
    public void testMostrarTodas() {
        MetodoE me1 = new MetodoE();
        me1.setId(1);
        me1.setNombre("Envío Express");
        me1.setTiempo(2);
        MetodoE me2 = new MetodoE();
        me2.setId(2);
        me2.setNombre("Envío Estándar");
        me2.setTiempo(5);
        when(metodoERepository.findAll()).thenReturn(Arrays.asList(me1, me2));

        List<MetodoEDTO> resultados = metodoEService.MostrarTodas();

        assertNotNull(resultados);
        assertEquals(2, resultados.size());
        assertEquals("Envío Express", resultados.get(0).getNombre());
        verify(metodoERepository, times(1)).findAll();
    }

    @Test
    public void testBuscarPorIdExistente() {
        MetodoE me = new MetodoE();
        me.setId(1);
        me.setNombre("Envío Express");
        me.setTiempo(2);
        when(metodoERepository.findById(1)).thenReturn(Optional.of(me));

        MetodoEDTO dto = metodoEService.buscarPorId(1);

        assertNotNull(dto);
        assertEquals(1, dto.getId());
        assertEquals("Envío Express", dto.getNombre());
        assertEquals(2, dto.getTiempo());
        verify(metodoERepository, times(1)).findById(1);
    }

    @Test
    public void testBuscarPorIdNoExistente() {
        when(metodoERepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> metodoEService.buscarPorId(99));
        verify(metodoERepository, times(1)).findById(99);
    }

    @Test
    public void testGuardarMetodoE() {
        MetodoE me = new MetodoE();
        me.setNombre("Recogida en tienda");
        me.setTiempo(0);
        MetodoE meGuardado = new MetodoE();
        meGuardado.setId(3);
        meGuardado.setNombre("Recogida en tienda");
        meGuardado.setTiempo(0);
        when(metodoERepository.save(any(MetodoE.class))).thenReturn(meGuardado);

        MetodoEDTO dto = metodoEService.guardarMetodoE(me);

        assertNotNull(dto);
        assertEquals(3, dto.getId());
        assertEquals("Recogida en tienda", dto.getNombre());
        verify(metodoERepository, times(1)).save(me);
    }

    @Test
    public void testEliminarMetodoEExistente() {
        MetodoE me = new MetodoE();
        me.setId(1);
        me.setNombre("Envío Express");
        when(metodoERepository.findById(1)).thenReturn(Optional.of(me));
        doNothing().when(metodoERepository).delete(me);

        String mensaje = metodoEService.EliminarMetodoE(1);

        assertEquals("Metodo de entrega Eliminada", mensaje);
        verify(metodoERepository, times(1)).findById(1);
        verify(metodoERepository, times(1)).delete(me);
    }
}