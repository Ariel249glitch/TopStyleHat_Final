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
import topstylehut.bloquecliente.Assemblers.RegionAssembler;
import topstylehut.bloquecliente.DTO.RegionDTO;
import topstylehut.bloquecliente.Model.Region;
import topstylehut.bloquecliente.Service.Regionservice; // Corregido el import a RegionService

@RestController
@RequestMapping("/api/v2/regiones")
@SuppressWarnings("all")
public class RegionControllerV2 {

    @Autowired
    private Regionservice regionService; // Corregido el tipo de servicio

    @Autowired
    private RegionAssembler assembler;
        
    // MOSTRAR TODOS
    @Operation(
        summary = "Mostrar todas las regiones", 
        description = "Muestra una lista de todas las regiones registradas"
    )
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<RegionDTO>>> MostrarTodos() {
        List<EntityModel<RegionDTO>> regiones = regionService.obtenerTodas().stream() // Conectado a obtenerTodas()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        
        if (regiones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
            regiones,
            linkTo(methodOn(RegionControllerV2.class).MostrarTodos()).withSelfRel()
        ));
    }

    // BUSCAR POR ID
    @Operation(
        summary = "Buscar región por ID",
        description = "Busca una región según su ID"
    )
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<RegionDTO>> BuscarRegionId(@PathVariable Integer id) {
        try {
            RegionDTO dto = regionService.buscarPorId(id);
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
        summary = "Registrar región",
        description = "Registra una nueva región en el sistema"
    )
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<RegionDTO>> registrar(@Valid @RequestBody Region region) {
        try {
            RegionDTO nuevaRegion = regionService.guardar(region); // Conectado a guardar()
            return ResponseEntity
                    .created(linkTo(methodOn(RegionControllerV2.class).BuscarRegionId(nuevaRegion.getId())).toUri())
                    .body(assembler.toModel(nuevaRegion));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // ACTUALIZAR
    @Operation(
        summary = "Editar región",
        description = "Edita la región por un id específico"
    )
    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<RegionDTO>> updateRegion(@PathVariable Integer id, @RequestBody Region region) {
        try {
            region.setId(id);
            RegionDTO regionDTO = regionService.guardar(region);
            return ResponseEntity.ok(assembler.toModel(regionDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ELIMINAR
    @Operation(
        summary = "Eliminar región",
        description = "Elimina la región por un id específico"
    )
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> deleteRegion(@PathVariable Integer id){
        try {
            regionService.eliminar(id); // Conectado a eliminar()
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}