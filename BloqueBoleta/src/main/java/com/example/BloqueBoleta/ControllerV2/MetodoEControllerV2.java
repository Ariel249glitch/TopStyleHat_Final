package com.example.BloqueBoleta.ControllerV2;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.BloqueBoleta.DTO.MetodoEDTO;
import com.example.BloqueBoleta.Model.MetodoE;
import com.example.BloqueBoleta.Service.MetodoEService;
import com.example.assemblers.MetodoEnvioAssembler;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
@RestController
@RequestMapping("/api/v2/metodos-entrega")

public class MetodoEControllerV2 {

    @Autowired
    private MetodoEService metodoEService;

    @Autowired
    private MetodoEnvioAssembler assembler;
        
    //mostrar todos
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<MetodoEDTO>>> MostrarTodos() {
        List<EntityModel<MetodoEDTO>> metodoEnvio = metodoEService.MostrarTodas().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        
        if (metodoEnvio.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
            metodoEnvio,
            linkTo(methodOn(MetodoEControllerV2.class).MostrarTodos()).withSelfRel()
        ));
    }



    //buscar por id

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<MetodoEDTO>> B_M_E_Id(@PathVariable Integer id) {
        try {
            MetodoEDTO dto = metodoEService.buscarPorId(id);
            if (dto == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(assembler.toModel(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    //Registrar

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<MetodoEDTO>> registrar(@Valid @RequestBody MetodoE Envio) {
        try {
            MetodoEDTO newEnvio = metodoEService.guardarMetodoE(Envio);
            return ResponseEntity
                    .created(linkTo(methodOn(MetodoEControllerV2.class).B_M_E_Id(newEnvio.getId())).toUri())
                    .body(assembler.toModel(newEnvio));
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
    public ResponseEntity<EntityModel<MetodoEDTO>> updateMetodoE(@PathVariable Integer id, @RequestBody MetodoE Envio) {
        try {
            MetodoE EnvioActualizado = metodoEService.actualizarMetodoE(id, Envio);

            MetodoEDTO metodoEDTO = metodoEService.buscarPorId(EnvioActualizado.getId());

            return ResponseEntity.ok(assembler.toModel(metodoEDTO));

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
        metodoEService.EliminarMetodoE(id);
        return ResponseEntity.noContent().build();
    }







    





}
