package com.example.BloqueGorro.ControllerV2;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


import com.example.BloqueGorro.DTO.TipoDTO;
import com.example.BloqueGorro.Model.Tipo;
import com.example.BloqueGorro.Service.TipoService;
import com.example.BloqueGorro.assemblers.TipoAssembler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

public class TipoControllerV2 {

    @Autowired
    private TipoService tipoService;

    @Autowired
    private TipoAssembler assembler;
    
    //mostrar todos
    @Operation(
        summary = "Mostrar todos los tipos",
        description = "Muestra una lista de todos los tipos registrados"
    )
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<TipoDTO>>> MostrarTodos() {
        List<EntityModel<TipoDTO>> tipos = tipoService.MostrarTodas().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        
        if (tipos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
            tipos,
            linkTo(methodOn(TipoControllerV2.class).MostrarTodos()).withSelfRel()
        ));
    }

    //buscar por id
    @Operation(
        summary = "Buscar tipo por ID",
        description = "Busca un tipo según su ID"
    )

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<TipoDTO>> BuscarTipoId(@PathVariable Integer id) {
        try {
            TipoDTO dto = tipoService.buscarPorId(id);
            if (dto == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(assembler.toModel(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    //guardar 
    @Operation(
        summary = "Registrar tipo",
        description = "Registra un nuevo tipo en el sistema"
    )
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<TipoDTO>> registrar(@Valid @RequestBody Tipo NuevoTipo) {
        try {
            TipoDTO newTipo = tipoService.guardarTipo(NuevoTipo);
            return ResponseEntity
                    .created(linkTo(methodOn(TipoControllerV2.class).BuscarTipoId(newTipo.getId())).toUri())
                    .body(assembler.toModel(newTipo));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    //actualizar
    @Operation(
        summary = "Editar tipo",
        description = "Edita el tipo por un id especifico"
    )
    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<TipoDTO>> updateTipo(@PathVariable Integer id, @RequestBody Tipo tipo) {
        try {
            Tipo tipoActualizado = tipoService.actualizarTipo(id, tipo);

            TipoDTO tipoDTO = tipoService.buscarPorId(tipoActualizado.getId());

            return ResponseEntity.ok(assembler.toModel(tipoDTO));

        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    //eliminar
    @Operation(
        summary = "Eliminar tipo",
        description = "Elimina el tipo por un id especifico"
    )
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteTipo(@PathVariable Integer id) {
        try {
            tipoService.EliminarTipo(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }



}
