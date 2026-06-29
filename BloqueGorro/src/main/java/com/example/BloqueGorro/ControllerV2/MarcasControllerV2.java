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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.BloqueGorro.DTO.MarcasDTO;
import com.example.BloqueGorro.Model.Marcas;
import com.example.BloqueGorro.Service.MarcasService;

import com.example.BloqueGorro.assemblers.MarcasAssembler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;



public class MarcasControllerV2 {

    @Autowired
    private MarcasService marcasService;

    @Autowired
    private MarcasAssembler assembler;

    //mostrar todas

    @Operation(
        summary = "Mostrar todas las marcas",
        description = "Muestra una lista de todas las marcas registradas"
    )
    
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<MarcasDTO>>> MostrarTodos() {
        List<EntityModel<MarcasDTO>> marcas = marcasService.MostrarTodas().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        
        if (marcas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
            marcas,
            linkTo(methodOn(MarcasControllerV2.class).MostrarTodos()).withSelfRel()
        ));
    }

    //buscar por id
    @Operation(
        summary = "Buscar marca por ID",
        description = "Busca una marca por su ID"
    )
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<MarcasDTO>> BuscarMarcaId(@PathVariable Integer id) {
        try {
            MarcasDTO dto = marcasService.buscarPorId(id);
            if (dto == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(assembler.toModel(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    //agregar
    @Operation(
        summary = "Agregar nueva marca",
        description = "Agrega una nueva marca al sistema"
    )
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<MarcasDTO>> agregar(@Valid @RequestBody Marcas nuevaMarca) {
        try {
            MarcasDTO newMarca = marcasService.guardarMarcas(nuevaMarca);
            return ResponseEntity
                    .created(linkTo(methodOn(MarcasControllerV2.class).BuscarMarcaId(newMarca.getId())).toUri())
                    .body(assembler.toModel(newMarca));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    //eliminar
    @Operation(
        summary = "Eliminar marcas",
        description = "Elimina la marca por un id especifico"
    )
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteMarca(@PathVariable Integer id) {
        try {
            marcasService.eliminarMarcas(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
