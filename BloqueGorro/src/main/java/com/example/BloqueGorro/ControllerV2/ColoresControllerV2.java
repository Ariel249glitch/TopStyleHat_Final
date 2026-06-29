package com.example.BloqueGorro.ControllerV2;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.example.BloqueGorro.DTO.ColoresDTO;
import com.example.BloqueGorro.Service.ColoresService;
import com.example.BloqueGorro.assemblers.ColoresAssemblers;

import io.swagger.v3.oas.annotations.Operation;


@RestController
@RequestMapping("/api/v2/colores")
public class ColoresControllerV2 {

    
    @Autowired
    private ColoresService coloresService;

    @Autowired
    private ColoresAssemblers assembler;

    //Mostrar todos ***

    @Operation(
        summary = "Mostrar todos los colores",
        description = "Muestra una lista de todos los colores registrados"
    )

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<ColoresDTO>>> MostrarTodos() {
        List<EntityModel<ColoresDTO>> colores = coloresService.obtenerTodas().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        
        if (colores.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
            colores,
            linkTo(methodOn(ColoresControllerV2.class).MostrarTodos()).withSelfRel()
        ));
    }

    //buscar por id ***

    @Operation(
        summary = "Buscar color por ID",
        description = "Busca un color según su ID"
    )
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ColoresDTO>> BuscarColoresPorId(@PathVariable Integer id) {
        try {
            ColoresDTO dto = coloresService.buscarPorId(id);
            if (dto == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(assembler.toModel(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }


    //buscar gorro por id
    @Operation(summary = "Buscar Gorro por su ID")
    @GetMapping("/gorro/{gorroId}")
    public ResponseEntity<?> buscarPorGorro(@PathVariable Integer gorroId) {
        List<ColoresDTO> relaciones = coloresService.buscarPorGorro(gorroId);
        if (relaciones.isEmpty()) {
            return new ResponseEntity<>("Este gorro no tiene colores asignados", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(relaciones, HttpStatus.OK);
    }

    //buscar por color
    @Operation(summary = "Buscar por color")
    @GetMapping("/color/{colorId}")
    public ResponseEntity<?> buscarPorColor(@PathVariable Integer colorId) {
        List<ColoresDTO> relaciones = coloresService.buscarPorColor(colorId);
        if (relaciones.isEmpty()) {
            return new ResponseEntity<>("Este color no está asignado a ningún gorro", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(relaciones, HttpStatus.OK);
    }

    //asignar color a gorro
    @Operation(summary = "Asignar color a gorro")
    @PostMapping("/gorro/{gorroId}/color/{colorId}")
    public ResponseEntity<?> asignarColorAGorro(@PathVariable Integer gorroId, @PathVariable Integer colorId) {
        try {
            ColoresDTO nuevaRelacion = coloresService.asignarColorAGorro(gorroId, colorId);
            return new ResponseEntity<>(nuevaRelacion, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    //eliminar relacion
    @Operation(summary = "Eliminar relacion")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarRelacion(@PathVariable Integer id) {
        String resultado = coloresService.eliminarRelacion(id);
        if (resultado.contains("exitosamente")) {
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
    }
}


