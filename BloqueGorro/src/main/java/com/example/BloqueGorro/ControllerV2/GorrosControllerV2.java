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

import com.example.BloqueGorro.DTO.GorrosDTO;
import com.example.BloqueGorro.Model.Gorros;
import com.example.BloqueGorro.Service.GorrosService;
import com.example.BloqueGorro.assemblers.GorrosAssembler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

public class GorrosControllerV2 {

    @Autowired
    private GorrosService gorrosService;

    @Autowired
    private GorrosAssembler assembler;

    //mostrar todos
    @Operation(
        summary = "Mostrar todos los gorros",
        description = "Muestra una lista de todos los gorros registrados"
    )
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<GorrosDTO>>> MostrarTodos() {
        List<EntityModel<GorrosDTO>> gorros = gorrosService.obtenerTodos().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        
        if (gorros.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
            gorros,
            linkTo(methodOn(GorrosControllerV2.class).MostrarTodos()).withSelfRel()
        ));
    }

    //buscar por id
    @Operation(
        summary = "Buscar gorro por ID",
        description = "Busca un gorro según su ID"
    )

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<GorrosDTO>> BuscarGorrosId(@PathVariable Integer id) {
        try {
            GorrosDTO dto = gorrosService.buscarPorId(id);
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
        summary = "Registrar gorro",
        description = "Registra un nuevo gorro en el sistema"
    )
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<GorrosDTO>> registrar(@Valid @RequestBody Gorros NuevoGorro) {
        try {
            GorrosDTO newGorro = gorrosService.guardarGorros(NuevoGorro);
            return ResponseEntity
                    .created(linkTo(methodOn(GorrosControllerV2.class).BuscarGorrosId(newGorro.getId())).toUri())
                    .body(assembler.toModel(newGorro));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    //Eliminar

    @Operation(
        summary = "Eliminar gorro",
        description = "Elimina el gorro por un id especifico"
    )
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteGorros(@PathVariable Integer id) {
        try {
            gorrosService.Eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    

}
