package topstylehut.bloquecliente.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import topstylehut.bloquecliente.DTO.ClienteDTO;
import topstylehut.bloquecliente.Model.Cliente;
import topstylehut.bloquecliente.Service.ClienteService;

@RestController
@RequestMapping("/api/v1/clientes")
@Tag(name = "Cliente", description = "Módulo de gestión de clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    // LISTAR TODOS
    @Operation(summary = "Listar todos los clientes", description = "Muestra todos los clientes")
    @GetMapping
    public ResponseEntity<List<ClienteDTO>> listarTodas() {
        List<ClienteDTO> lista = clienteService.obtenerTodos();

        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    // BUSCAR POR ID
    @Operation(summary = "Buscar cliente por ID", description = "Busca un cliente por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        try {
            ClienteDTO clienteDTO = clienteService.buscarPorId(id);
            return new ResponseEntity<>(clienteDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Cliente no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    // CREAR
    @Operation(summary = "Registrar nuevo cliente", description = "Registra un nuevo cliente en el sistema")
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Cliente nuevoCliente) {
        try {
            ClienteDTO dto = clienteService.guardar(nuevoCliente);
            return new ResponseEntity<>(dto, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al guardar cliente", HttpStatus.BAD_REQUEST);
        }
    }

    // ACTUALIZAR
    @Operation(summary = "Actualizar cliente por ID", description = "Actualizar un cliente por su ID")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody Cliente cliente) {
        try {
            cliente.setId(id);
            ClienteDTO dto = clienteService.guardar(cliente);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // ELIMINAR
    @Operation(summary = "Eliminar cliente por ID", description = "Elimina un cliente del sistema")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            String resultado = clienteService.eliminar(id);
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}