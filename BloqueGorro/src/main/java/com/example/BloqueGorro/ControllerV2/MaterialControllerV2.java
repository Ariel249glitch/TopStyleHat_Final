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


import com.example.BloqueGorro.DTO.MaterialDTO;
import com.example.BloqueGorro.Model.Material;
import com.example.BloqueGorro.Service.MaterialService;
import com.example.BloqueGorro.assemblers.MaterialAssembler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

public class MaterialControllerV2 {

    @Autowired
    private MaterialService materialService;

    @Autowired
    private MaterialAssembler assembler;
    
    //mostrar todos
    @Operation(
        summary = "Mostrar todo el material",
        description = "Muestra una lista de todos los materiales registrados"
    )
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<MaterialDTO>>> MostrarTodos() {
        List<EntityModel<MaterialDTO>> materiales = materialService.mostrarTodos().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        
        if (materiales.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
            materiales,
            linkTo(methodOn(MaterialControllerV2.class).MostrarTodos()).withSelfRel()
        ));
    }

    //buscar por id
    @Operation(
        summary = "Buscar material por ID",
        description = "Busca un material según su ID"
    )

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<MaterialDTO>> BuscarMaterialId(@PathVariable Integer id) {
        try {
            MaterialDTO dto = materialService.buscarPorId(id);
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
        summary = "Registrar material",
        description = "Registra un nuevo material en el sistema"
    )
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<MaterialDTO>> registrar(@Valid @RequestBody Material NuevoMaterial) {
        try {
            MaterialDTO newMaterial = materialService.guardarMaterial(NuevoMaterial);
            return ResponseEntity
                    .created(linkTo(methodOn(MaterialControllerV2.class).BuscarMaterialId(newMaterial.getId())).toUri())
                    .body(assembler.toModel(newMaterial));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    //actualizar
    @Operation(
        summary = "Editar material",
        description = "Edita el material por un id especifico"
    )
    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<MaterialDTO>> updateMaterial(@PathVariable Integer id, @RequestBody Material material) {
        try {
            Material materialActualizado = materialService.actualizarMaterial(id, material);

            MaterialDTO materialDTO = materialService.buscarPorId(materialActualizado.getId());

            return ResponseEntity.ok(assembler.toModel(materialDTO));

        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    //eliminar
    @Operation(
        summary = "Eliminar material",
        description = "Elimina el material por un id especifico"
    )
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteMaterial(@PathVariable Integer id) {
        try {
            materialService.eliminarMaterial(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
