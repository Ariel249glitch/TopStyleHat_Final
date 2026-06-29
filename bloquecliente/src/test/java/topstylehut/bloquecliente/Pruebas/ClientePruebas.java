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

import topstylehut.bloquecliente.DTO.ClienteDTO;
import topstylehut.bloquecliente.Model.Cliente;
import topstylehut.bloquecliente.Repository.ClienteRepository;
import topstylehut.bloquecliente.Service.ClienteService;

@ExtendWith(MockitoExtension.class)
public class ClientePruebas {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cliente = new Cliente();
        cliente.setId(1);
        cliente.setNombre("Juan Perez");
        cliente.setDireccion("Av. Siempre Viva 742");
    }

    @Test
    void testObtenerTodos() {
        when(clienteRepository.findAll()).thenReturn(Collections.singletonList(cliente));
        List<ClienteDTO> resultado = clienteService.obtenerTodos();
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Juan Perez", resultado.get(0).getNombre());
    }

    @Test
    void testBuscarPorId_Exitoso() {
        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));
        ClienteDTO resultado = clienteService.buscarPorId(1);
        assertNotNull(resultado);
        assertEquals("Juan Perez", resultado.getNombre());
    }

    @Test
    void testBuscarPorId_NoEncontrado() {
        when(clienteRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> clienteService.buscarPorId(1));
    }

    @Test
    void testGuardar() {
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);
        ClienteDTO resultado = clienteService.guardar(cliente);
        assertNotNull(resultado);
        assertEquals("Juan Perez", resultado.getNombre());
    }

    @Test
    void testEliminar_Exitoso() {
        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));
        doNothing().when(clienteRepository).delete(cliente);
        String mensaje = clienteService.eliminar(1);
        assertTrue(mensaje.contains("eliminado exitosamente"));
    }
}