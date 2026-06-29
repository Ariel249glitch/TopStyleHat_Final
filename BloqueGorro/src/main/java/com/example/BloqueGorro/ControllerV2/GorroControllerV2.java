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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


import com.example.BloqueGorro.DTO.GorroDTO;
import com.example.BloqueGorro.Model.Gorro;
import com.example.BloqueGorro.Service.GorroService;
import com.example.BloqueGorro.assemblers.GorroAssembler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;



@RestController
@RequestMapping("/api/v2/Gorro")
public class GorroControllerV2 {

    @Autowired
    private GorroService gorroService;

    @Autowired
    private GorroAssembler assembler;
    
    //mostrar todos
    @Operation(
        summary = "Mostrar todos los gorros",
        description = "Muestra una lista de todos los gorros registrados"
    )
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<GorroDTO>>> MostrarTodos() {
        List<EntityModel<GorroDTO>> gorros = gorroService.obtenerTodos().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        
        if (gorros.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
            gorros,
            linkTo(methodOn(GorroControllerV2.class).MostrarTodos()).withSelfRel()
        ));
    }

    //buscar por id
    @Operation(
        summary = "Buscar gorro por ID",
        description = "Busca un gorro según su ID"
    )

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<GorroDTO>> BuscarGorroId(@PathVariable Integer id) {
        try {
            GorroDTO dto = gorroService.buscarPorId(id);
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
    public ResponseEntity<EntityModel<GorroDTO>> registrar(@Valid @RequestBody Gorro NuevoGorro) {
        try {
            GorroDTO newGorro = gorroService.guardarGorro(NuevoGorro);
            return ResponseEntity
                    .created(linkTo(methodOn(GorroControllerV2.class).BuscarGorroId(newGorro.getId())).toUri())
                    .body(assembler.toModel(newGorro));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    //actualizar
    @Operation(
        summary = "Editar gorro",
        description = "Edita el gorro por un id especifico"
    )
    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<GorroDTO>> updateGorro(@PathVariable Integer id, @RequestBody Gorro gorro) {
        try {
            Gorro gorroActualizado = gorroService.ActualizarGorro(id, gorro);

            GorroDTO gorroDTO = gorroService.buscarPorId(gorroActualizado.getId());

            return ResponseEntity.ok(assembler.toModel(gorroDTO));

        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    //eliminar
    @Operation(
        summary = "Eliminar gorro",
        description = "Elimina el gorro por un id especifico"
    )
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteGorro(@PathVariable Integer id) {
        try {
            gorroService.Eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
