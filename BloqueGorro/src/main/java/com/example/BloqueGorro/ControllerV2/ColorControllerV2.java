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

import com.example.BloqueGorro.DTO.ColorDTO;
import com.example.BloqueGorro.Model.Color;
import com.example.BloqueGorro.Service.ColorService;
import com.example.BloqueGorro.assemblers.ColorAssembler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v2/color")
public class ColorControllerV2 {

    @Autowired
    private ColorService colorService;

    @Autowired
    private ColorAssembler assembler;
        
    //mostrar todos

    @Operation(
        summary = "Mostrar todos los colores",
        description = "Muestra una lista de todos los colores registrados"
    )
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<ColorDTO>>> MostrarTodos() {
        List<EntityModel<ColorDTO>> color = colorService.obtenerTodos().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        
        if (color.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
            color,
            linkTo(methodOn(ColorControllerV2.class).MostrarTodos()).withSelfRel()
        ));
    }



    //buscar por id

    @Operation(
        summary = "Buscar color por ID",
        description = "Busca un color según su ID"
    )
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ColorDTO>> BuscarColorId(@PathVariable Integer id) {
        try {
            ColorDTO dto = colorService.buscarPorId(id);
            if (dto == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(assembler.toModel(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    //Registrar
    @Operation(
        summary = "Registrar color",
        description = "Registra un nuevo color en el sistema"
    )
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ColorDTO>> registrar(@Valid @RequestBody Color color) {
        try {
            ColorDTO newColor = colorService.guardar(color);
            return ResponseEntity
                    .created(linkTo(methodOn(ColorControllerV2.class).BuscarColorId(newColor.getId())).toUri())
                    .body(assembler.toModel(newColor));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    //Actualizar

    @Operation(
        summary = "Editar Metodo de envio",
        description = "Edita el metodo de envio por un id especifico"
    )
    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ColorDTO>> updateMetodoE(@PathVariable Integer id, @RequestBody Color color) {
        try {
            Color ColorActualizado = colorService.actualizar(id, color);

            ColorDTO colorDTO = colorService.buscarPorId(ColorActualizado.getId());

            return ResponseEntity.ok(assembler.toModel(colorDTO));

        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    //Eliminar
    @Operation(
        summary = "Elimina un Metodo de Envio",
        description = "elimina el Metodo de envio por un id especifico"
    )
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> deleteMetodoDeEnvio(@PathVariable Integer id){
        colorService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
