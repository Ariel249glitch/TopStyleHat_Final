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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.BloqueGorro.DTO.EstilosDTO;
import com.example.BloqueGorro.Service.EstilosService;
import com.example.BloqueGorro.assemblers.EstilosAssembler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v2/estilos")
public class EstilosControllerV2 {

    @Autowired
    private EstilosService estilosService;

    @Autowired
    private EstilosAssembler assembler;

    //mostrar todos

    @Operation(
        summary = "Mostrar todos los estilos",
        description = "Muestra una lista de todos los estilos registrados"
    )

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<EstilosDTO>>> MostrarTodos() {
        List<EntityModel<EstilosDTO>> estilos = estilosService.MostrarTodas().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        
        if (estilos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
            estilos,
            linkTo(methodOn(EstilosControllerV2.class).MostrarTodos()).withSelfRel()
        ));
    }

    //registrar
    @Operation(
        summary = "Registrar estilo",
        description = "Registra un nuevo estilo en el sistema"
    )
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<EstilosDTO>> registrar(@Valid @RequestBody EstilosDTO estilos) {
        try {
            EstilosDTO newEstilos = estilosService.saveDTO(estilos);

            return ResponseEntity.created(
                    linkTo(methodOn(EstilosControllerV2.class).MostrarTodos()).toUri())
                    .body(assembler.toModel(newEstilos));

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        }

    
    //Eliminar

    @Operation(
        summary = "Elimina un estilo",
        description = "elimina el estilo por un id especifico"
    )
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> deleteEstilo(@PathVariable Integer id){
        estilosService.EliminarEstilos(id);
        return ResponseEntity.noContent().build();
    }



    }
    


