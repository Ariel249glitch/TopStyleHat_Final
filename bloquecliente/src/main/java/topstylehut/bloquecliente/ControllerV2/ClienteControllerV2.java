package topstylehut.bloquecliente.ControllerV2;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController; // Corregido el import de RequestBody para que funcione bien en Spring

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import topstylehut.bloquecliente.Assemblers.ClienteAssembler;
import topstylehut.bloquecliente.DTO.ClienteDTO;
import topstylehut.bloquecliente.Model.Cliente;
import topstylehut.bloquecliente.Service.ClienteService;

@RestController
@RequestMapping("/api/v2/clientes")
public class ClienteControllerV2 {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClienteAssembler assembler;
        
    // LISTAR TODOS
    @Operation(
        summary = "Listar todos los clientes", 
        description = "Muestra una lista de todos los clientes registrados"
    )
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<ClienteDTO>>> MostrarTodos() {
        List<EntityModel<ClienteDTO>> clientes = clienteService.obtenerTodos().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        
        if (clientes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
            clientes,
            linkTo(methodOn(ClienteControllerV2.class).MostrarTodos()).withSelfRel()
        ));
    }

    // BUSCAR POR ID
    @Operation(
        summary = "Buscar cliente por ID",
        description = "Busca un cliente según su ID"
    )
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ClienteDTO>> BuscarClienteId(@PathVariable Integer id) {
        try {
            ClienteDTO dto = clienteService.buscarPorId(id);
            if (dto == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(assembler.toModel(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // CREAR
    @Operation(
        summary = "Registrar cliente",
        description = "Registra un nuevo cliente en el sistema"
    )
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ClienteDTO>> registrar(@Valid @RequestBody Cliente cliente) {
        try {
            ClienteDTO dto = clienteService.guardar(cliente);
            return ResponseEntity
                    .created(linkTo(methodOn(ClienteControllerV2.class).BuscarClienteId(dto.getId())).toUri())
                    .body(assembler.toModel(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // ACTUALIZAR
    @Operation(
        summary = "Editar cliente",
        description = "Edita el cliente por un id específico"
    )
    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ClienteDTO>> updateCliente(@PathVariable Integer id, @RequestBody Cliente cliente) {
        try {
            // Adaptado para guardar el cambio usando el método del servicio
            cliente.setId(id);
            ClienteDTO clienteDTO = clienteService.guardar(cliente);
            return ResponseEntity.ok(assembler.toModel(clienteDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ELIMINAR
    @Operation(
        summary = "Eliminar cliente",
        description = "Elimina el cliente por un id específico"
    )
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> deleteCliente(@PathVariable Integer id){
        try {
            clienteService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}