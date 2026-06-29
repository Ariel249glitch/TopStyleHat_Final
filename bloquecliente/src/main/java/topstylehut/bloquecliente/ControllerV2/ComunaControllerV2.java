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
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import topstylehut.bloquecliente.Assemblers.ComunaAssembler;
import topstylehut.bloquecliente.DTO.ComunaDTO;
import topstylehut.bloquecliente.Model.Comuna;
import topstylehut.bloquecliente.Service.ComunaService;

@RestController
@RequestMapping("/api/v2/comunas")
@SuppressWarnings("all")
public class ComunaControllerV2 {

    @Autowired
    private ComunaService comunaService;

    @Autowired
    private ComunaAssembler assembler;
        
    // MOSTRAR TODOS
    @Operation(
        summary = "Mostrar todas las comunas", 
        description = "Muestra una lista de todas las comunas registradas"
    )
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<ComunaDTO>>> MostrarTodos() {
        List<EntityModel<ComunaDTO>> comunas = comunaService.obtenerTodas().stream() // Conectado a obtenerTodas()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        
        if (comunas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
            comunas,
            linkTo(methodOn(ComunaControllerV2.class).MostrarTodos()).withSelfRel()
        ));
    }

    // BUSCAR POR ID
    @Operation(
        summary = "Buscar comuna por ID",
        description = "Busca una comuna según su ID"
    )
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ComunaDTO>> BuscarComunaId(@PathVariable Integer id) {
        try {
            ComunaDTO dto = comunaService.buscarPorId(id);
            if (dto == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(assembler.toModel(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // REGISTRAR
    @Operation(
        summary = "Registrar comuna",
        description = "Registra una nueva comuna en el sistema"
    )
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ComunaDTO>> registrar(@Valid @RequestBody Comuna comuna) {
        try {
            ComunaDTO nuevaComuna = comunaService.guardar(comuna); // Conectado a guardar()
            return ResponseEntity
                    .created(linkTo(methodOn(ComunaControllerV2.class).BuscarComunaId(nuevaComuna.getId())).toUri())
                    .body(assembler.toModel(nuevaComuna));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // ACTUALIZAR
    @Operation(
        summary = "Editar comuna",
        description = "Edita la comuna por un id específico"
    )
    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ComunaDTO>> updateComuna(@PathVariable Integer id, @RequestBody Comuna comuna) {
        try {
            comuna.setId(id);
            ComunaDTO comunaDTO = comunaService.guardar(comuna);
            return ResponseEntity.ok(assembler.toModel(comunaDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ELIMINAR
    @Operation(
        summary = "Eliminar comuna",
        description = "Elimina la comuna por un id específico"
    )
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> deleteComuna(@PathVariable Integer id){
        try {
            comunaService.eliminar(id); // Conectado a eliminar()
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}