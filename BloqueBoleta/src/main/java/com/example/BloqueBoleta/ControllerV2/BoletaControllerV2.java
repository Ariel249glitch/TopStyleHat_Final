package com.example.BloqueBoleta.ControllerV2;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.BloqueBoleta.DTO.BoletaDTO;
import com.example.BloqueBoleta.Model.Boleta;
import com.example.BloqueBoleta.Service.BoletaService;
import com.example.assemblers.BoletaControllerAssabler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v2/boletas")
@SuppressWarnings("all")
public class BoletaControllerV2 {

    @Autowired
    private BoletaService boletaService;

    @Autowired
    private BoletaControllerAssabler assembler;
        
    @Operation(
        summary = "Mostrar todas las boletas", 
        description = "Muestra una lista de todas las boletas registradas"
    )
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<BoletaDTO>>> MostrarTodos() {
        List<EntityModel<BoletaDTO>> boletas = boletaService.obtenerTodas().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        
        if (boletas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
            boletas,
            linkTo(methodOn(BoletaControllerV2.class).MostrarTodos()).withSelfRel()
        ));
    }

    @Operation(
        summary = "Buscar boleta por ID",
        description = "Busca una boleta según su ID"
    )
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<BoletaDTO>> BuscarBoletaId(@PathVariable Integer id) {
        try {
            BoletaDTO dto = boletaService.buscarPorId(id);
            if (dto == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(assembler.toModel(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
        summary = "Registrar boleta",
        description = "Registra una nueva boleta en el sistema"
    )
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<BoletaDTO>> registrar(@Valid @RequestBody Boleta boleta) {
        try {
            Boleta guardada = boletaService.guardar(boleta);
            BoletaDTO dto = boletaService.convertirADTO(guardada);
            return ResponseEntity
                    .created(linkTo(methodOn(BoletaControllerV2.class).BuscarBoletaId(dto.getId())).toUri())
                    .body(assembler.toModel(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(
        summary = "Editar boleta",
        description = "Edita la boleta por un id específico"
    )
    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<BoletaDTO>> updateBoleta(@PathVariable Integer id, @RequestBody Boleta boleta) {
        try {
            Boleta actualizada = boletaService.actualizar(id, boleta);
            BoletaDTO dto = boletaService.convertirADTO(actualizada);
            return ResponseEntity.ok(assembler.toModel(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
        summary = "Eliminar boleta",
        description = "Elimina la boleta por un id específico"
    )
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> deleteBoleta(@PathVariable Integer id){
        try {
            boletaService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}