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


import com.example.BloqueGorro.DTO.MarcaDTO;
import com.example.BloqueGorro.Model.Marca;
import com.example.BloqueGorro.Service.MarcaService;
import com.example.BloqueGorro.assemblers.MarcaAssembler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;



public class MarcaControllerV2 {

    @Autowired
    private MarcaService marcaService;

    @Autowired
    private MarcaAssembler assembler;

    //mostrar todos

    @Operation(
        summary = "Mostrar todas las marcas",
        description = "Muestra una lista de todas las marcas registradas"
    )
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<MarcaDTO>>> MostrarTodos() {
        List<EntityModel<MarcaDTO>> marca = marcaService.MostrarTodas().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        
        if (marca.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
            marca,
            linkTo(methodOn(MarcaControllerV2.class).MostrarTodos()).withSelfRel()
        ));
    }


    //buscar por id
    @Operation(
        summary = "Buscar marca por ID",
        description = "Busca una marca según su ID"
    )

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<MarcaDTO>> BuscarMarcaId(@PathVariable Integer id) {
        try {
            MarcaDTO dto = marcaService.buscarPorId(id);
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
        summary = "Registrar marca",
        description = "Registra una nueva marca en el sistema"
    )
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<MarcaDTO>> registrar(@Valid @RequestBody Marca NuevaMarca) {
        try {
            MarcaDTO newMarca = marcaService.guardarMarca(NuevaMarca);
            return ResponseEntity
                    .created(linkTo(methodOn(MarcaControllerV2.class).BuscarMarcaId(newMarca.getId())).toUri())
                    .body(assembler.toModel(newMarca));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    
    //actualizar
    @Operation(
        summary = "Editar marca",
        description = "Edita la marca por un id especifico"
    )
    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<MarcaDTO>> updateMarca(@PathVariable Integer id, @RequestBody Marca marca) {
        try {
            Marca marcaActualizada = marcaService.actualizarMarca(id, marca);

            MarcaDTO marcaDTO = marcaService.buscarPorId(marcaActualizada.getId());

            return ResponseEntity.ok(assembler.toModel(marcaDTO));

        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    //eliminar
    @Operation(
        summary = "Eliminar marca",
        description = "Elimina la marca por un id especifico"
    )
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteMarca(@PathVariable Integer id) {
        try {
            marcaService.EliminarMarca(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
