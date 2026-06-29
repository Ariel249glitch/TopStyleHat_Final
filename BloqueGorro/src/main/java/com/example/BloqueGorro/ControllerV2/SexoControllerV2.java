package com.example.BloqueGorro.ControllerV2;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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


import com.example.BloqueGorro.DTO.SexoDTO;

import com.example.BloqueGorro.Model.Sexo;
import com.example.BloqueGorro.Service.SexoService;
import com.example.BloqueGorro.assemblers.SexoAssembler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

public class SexoControllerV2 {

    @Autowired
    private SexoService sexoService;

    @Autowired
    private SexoAssembler assembler;

    //mostrar todos
    @Operation(
        summary = "Mostrar todos los sexos",
        description = "Muestra una lista de todos los sexos registrados"
    )
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<SexoDTO>>> MostrarTodos() {
        List<EntityModel<SexoDTO>> sexos = sexoService.mostrarTodos().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        
        if (sexos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
            sexos,
            linkTo(methodOn(SexoControllerV2.class).MostrarTodos()).withSelfRel()
        ));
    }

    //buscar por id
    @Operation(
        summary = "Buscar sexo por ID",
        description = "Busca un sexo según su ID"
    )

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<SexoDTO>> BuscarSexoId(@PathVariable Integer id) {
        try {
            SexoDTO dto = sexoService.buscarPorId(id);
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
        summary = "Registrar sexo",
        description = "Registra un nuevo sexo en el sistema"
    )
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<SexoDTO>> registrar(@Valid @RequestBody Sexo NuevoSexo) {
        try {
            SexoDTO newSexo = sexoService.guardarSexo(NuevoSexo);
            return ResponseEntity
                    .created(linkTo(methodOn(SexoControllerV2.class).BuscarSexoId(newSexo.getId())).toUri())
                    .body(assembler.toModel(newSexo));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    //actualizar
    @Operation(
        summary = "Editar sexo",
        description = "Edita el sexo por un id especifico"
    )
    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<SexoDTO>> updateSexo(@PathVariable Integer id, @RequestBody Sexo sexo) {
        try {
            Sexo sexoActualizado = sexoService.actualizarSexo(id, sexo);

            SexoDTO sexoDTO = sexoService.buscarPorId(sexoActualizado.getId());

            return ResponseEntity.ok(assembler.toModel(sexoDTO));

        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    //eliminar
    @Operation(
        summary = "Eliminar sexo",
        description = "Elimina el sexo por un id especifico"
    )
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteSexo(@PathVariable Integer id) {
        try {
            sexoService.eliminarSexo(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
