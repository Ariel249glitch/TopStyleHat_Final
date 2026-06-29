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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


import com.example.BloqueGorro.DTO.MaterialesDTO;
import com.example.BloqueGorro.Model.Materiales;
import com.example.BloqueGorro.Service.MaterialesService;
import com.example.BloqueGorro.assemblers.MaterialesAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

public class MaterialesControllerV2 {

    @Autowired
    private MaterialesService materialesService;

    @Autowired
    private MaterialesAssembler assembler;

    //mostrar todos
    @Operation(
        summary = "Mostrar todo los materiales",
        description = "Muestra una lista de todos los materiales registrados"
    )
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<MaterialesDTO>>> MostrarTodos() {
        List<EntityModel<MaterialesDTO>> materiales = materialesService.obtenerTodos().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        
        if (materiales.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
            materiales,
            linkTo(methodOn(MaterialesControllerV2.class).MostrarTodos()).withSelfRel()
        ));
    }

    //buscar por id
    @Operation(
        summary = "Buscar materiales por ID",
        description = "Busca unos materiales según su ID"
    )

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<MaterialesDTO>> BuscarMaterialesId(@PathVariable Integer id) {
        try {
            MaterialesDTO dto = materialesService.buscarPorId(id);
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
        summary = "Registrar materiales",
        description = "Registra nuevos materiales en el sistema"
    )
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<MaterialesDTO>> registrar(@Valid @RequestBody Materiales NuevosMateriales) {
        try {
            MaterialesDTO newMateriales = materialesService.guardarMateriales(NuevosMateriales);
            return ResponseEntity
                    .created(linkTo(methodOn(MaterialesControllerV2.class).BuscarMaterialesId(newMateriales.getId())).toUri())
                    .body(assembler.toModel(newMateriales));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    //eliminar
    @Operation(
        summary = "Eliminar materiales",
        description = "Elimina los materiales por un id especifico"
    )
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteMateriales(@PathVariable Integer id) {
        try {
            materialesService.eliminarMateriales(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
