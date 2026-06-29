package com.example.BloqueGorro.ControllerV2;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.BloqueGorro.DTO.EstiloDTO;
import com.example.BloqueGorro.Service.EstiloService;
import com.example.BloqueGorro.assemblers.EstiloAssembler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v2/estilo")
public class EstiloControllerV2 {

    @Autowired
    private EstiloService estiloService;

    @Autowired
    private EstiloAssembler assembler;

    //mostrar todos

    @Operation(
        summary = "Mostrar todos los estilos",
        description = "Muestra una lista de todos los estilos registrados"
    )
    
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<EstiloDTO>>> MostrarTodos() {
        List<EntityModel<EstiloDTO>> estilo = estiloService.MostrarTodas().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        
        if (estilo.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
            estilo,
            linkTo(methodOn(EstiloControllerV2.class).MostrarTodos()).withSelfRel()
        ));
    }

    //buscar por id
    @Operation(
        summary = "Buscar estilo por ID",
        description = "Busca un estilo según su ID"
    )
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<EstiloDTO>> BuscarEstiloId(@PathVariable Integer id) {
        try {
            EstiloDTO dto = estiloService.buscarPorId(id);
            if (dto == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(assembler.toModel(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    //guardar un nuevo registro
    @Operation(
        summary = "Registrar estilo",
        description = "Registra un nuevo estilo en el sistema"
    )
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<EstiloDTO>> registrar(@Valid @RequestBody EstiloDTO estilo) {
        try {
            EstiloDTO newEstilo = estiloService.guardarEstilo(estilo);
            return ResponseEntity
                    .created(linkTo(methodOn(EstiloControllerV2.class).BuscarEstiloId(newEstilo.getId())).toUri())
                    .body(assembler.toModel(newEstilo));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    

    // Actualizar un registro existente

    @Operation(
        summary = "Editar estilo",
        description = "Edita el estilo por un id especifico"
    )
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<EstiloDTO>> actualizar(
        @PathVariable Integer id,
        @Valid @RequestBody EstiloDTO estilo) {

        try {

            EstiloDTO updatedEstilo = estiloService.actualizarEsti(id, estilo);

            return ResponseEntity.ok(
                assembler.toModel(updatedEstilo)
            );

        } catch (RuntimeException e) {

            return ResponseEntity.notFound().build();

        }
    }

    //Eliminar
    @Operation(
        summary = "Elimina un Estilo",
        description = "elimina el Estilo por un id especifico"
    )
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> deleteEstilo(@PathVariable Integer id){
        estiloService.EliminarEstilo(id);
        return ResponseEntity.noContent().build();
    }

}
