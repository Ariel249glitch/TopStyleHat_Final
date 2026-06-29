package com.example.BloqueBoleta.Service;

import com.example.BloqueBoleta.DTO.BoletaDTO;
import com.example.BloqueBoleta.DTO.ClienteDTOExterno;
import com.example.BloqueBoleta.DTO.GorroDTOExterno;
import com.example.BloqueBoleta.Model.Boleta;
import com.example.BloqueBoleta.Model.MetodoE;
import com.example.BloqueBoleta.Model.MetodoPago;
import com.example.BloqueBoleta.Repository.BoletaRepository;
import com.example.BloqueBoleta.Service.BoletaService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BoletaServiceTest {

    @Autowired
    private BoletaService boletaService;

    @MockBean
    private BoletaRepository boletaRepository;

    @MockBean
    private BoletaValidaciones boletaValidaciones;

    @Test
    public void testObtenerTodas() {
        // Crear datos de prueba
        MetodoPago mp = new MetodoPago();
        mp.setId(1);
        mp.setNombre("Efectivo");
        MetodoE me = new MetodoE();
        me.setId(1);
        me.setNombre("Envío Express");

        Boleta b1 = new Boleta();
        b1.setId(1);
        b1.setFecha(LocalDate.now());
        b1.setMetodoPago(mp);
        b1.setMetodoEnvio(me);
        b1.setClienteId(10);
        b1.setGorroId(20);

        Boleta b2 = new Boleta();
        b2.setId(2);
        b2.setFecha(LocalDate.now());
        b2.setMetodoPago(mp);
        b2.setMetodoEnvio(me);
        b2.setClienteId(11);
        b2.setGorroId(21);

        when(boletaRepository.findAll()).thenReturn(Arrays.asList(b1, b2));

        // Mockear las validaciones externas
        ClienteDTOExterno cliente1 = new ClienteDTOExterno();
        cliente1.setId(10);
        cliente1.setNombre("Juan");
        GorroDTOExterno gorro1 = new GorroDTOExterno();
        gorro1.setId(20);
        gorro1.setNombre("Gorro A");
        when(boletaValidaciones.obtenerCliente(10)).thenReturn(cliente1);
        when(boletaValidaciones.obtenerGorro(20)).thenReturn(gorro1);

        ClienteDTOExterno cliente2 = new ClienteDTOExterno();
        cliente2.setId(11);
        cliente2.setNombre("María");
        GorroDTOExterno gorro2 = new GorroDTOExterno();
        gorro2.setId(21);
        gorro2.setNombre("Gorro B");
        when(boletaValidaciones.obtenerCliente(11)).thenReturn(cliente2);
        when(boletaValidaciones.obtenerGorro(21)).thenReturn(gorro2);

        // Ejecutar
        List<BoletaDTO> resultados = boletaService.obtenerTodas();

        // Verificar
        assertNotNull(resultados);
        assertEquals(2, resultados.size());
        assertEquals("Juan", resultados.get(0).getCliente().getNombre());
        assertEquals("Gorro B", resultados.get(1).getGorro().getNombre());
        verify(boletaRepository, times(1)).findAll();
    }

    @Test
    public void testBuscarPorIdExistente() {
        MetodoPago mp = new MetodoPago();
        mp.setId(1);
        mp.setNombre("Efectivo");
        MetodoE me = new MetodoE();
        me.setId(1);
        me.setNombre("Envío Express");

        Boleta b = new Boleta();
        b.setId(1);
        b.setFecha(LocalDate.now());
        b.setMetodoPago(mp);
        b.setMetodoEnvio(me);
        b.setClienteId(10);
        b.setGorroId(20);

        when(boletaRepository.findById(1)).thenReturn(Optional.of(b));

        ClienteDTOExterno cliente = new ClienteDTOExterno();
        cliente.setId(10);
        cliente.setNombre("Juan");
        GorroDTOExterno gorro = new GorroDTOExterno();
        gorro.setId(20);
        gorro.setNombre("Gorro A");
        when(boletaValidaciones.obtenerCliente(10)).thenReturn(cliente);
        when(boletaValidaciones.obtenerGorro(20)).thenReturn(gorro);

        BoletaDTO dto = boletaService.buscarPorId(1);

        assertNotNull(dto);
        assertEquals(1, dto.getId());
        assertEquals("Efectivo", dto.getMetodoPago());
        assertEquals("Envío Express", dto.getMetodoEnvio());
        verify(boletaRepository, times(1)).findById(1);
    }

    @Test
    public void testBuscarPorIdNoExistente() {
        when(boletaRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> boletaService.buscarPorId(99));
        verify(boletaRepository, times(1)).findById(99);
    }

    @Test
    public void testGuardar() {
        MetodoPago mp = new MetodoPago();
        mp.setId(1);
        mp.setNombre("Efectivo");
        MetodoE me = new MetodoE();
        me.setId(1);
        me.setNombre("Envío Express");

        Boleta b = new Boleta();
        b.setFecha(LocalDate.now());
        b.setMetodoPago(mp);
        b.setMetodoEnvio(me);
        b.setClienteId(10);
        b.setGorroId(20);

        Boleta bGuardada = new Boleta();
        bGuardada.setId(1);
        bGuardada.setFecha(b.getFecha());
        bGuardada.setMetodoPago(mp);
        bGuardada.setMetodoEnvio(me);
        bGuardada.setClienteId(10);
        bGuardada.setGorroId(20);

        when(boletaRepository.save(any(Boleta.class))).thenReturn(bGuardada);

        Boleta resultado = boletaService.guardar(b);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals(10, resultado.getClienteId());
        verify(boletaRepository, times(1)).save(b);
    }

    @Test
    public void testEliminarExistente() {
        MetodoPago mp = new MetodoPago();
        mp.setId(1);
        mp.setNombre("Efectivo");
        MetodoE me = new MetodoE();
        me.setId(1);
        me.setNombre("Envío Express");

        Boleta b = new Boleta();
        b.setId(1);
        b.setFecha(LocalDate.now());
        b.setMetodoPago(mp);
        b.setMetodoEnvio(me);
        b.setClienteId(10);
        b.setGorroId(20);

        when(boletaRepository.findById(1)).thenReturn(Optional.of(b));
        doNothing().when(boletaRepository).delete(b);

        String mensaje = boletaService.eliminar(1);

        assertEquals("La boleta 1 ha sido eliminada exitosamente", mensaje);
        verify(boletaRepository, times(1)).findById(1);
        verify(boletaRepository, times(1)).delete(b);
    }
}