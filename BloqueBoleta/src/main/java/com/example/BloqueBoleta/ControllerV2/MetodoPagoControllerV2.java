package com.example.BloqueBoleta.ControllerV2;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.BloqueBoleta.DTO.MetodoPagoDTO;
import com.example.BloqueBoleta.Model.MetodoPago;
import com.example.BloqueBoleta.Service.MetodoPagoService;
import com.example.assemblers.MetodoPagoAssembler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v2/metodos-pago")
@SuppressWarnings("all")
public class MetodoPagoControllerV2 {

    @Autowired
    private MetodoPagoService metodoPagoService;

    @Autowired
    private MetodoPagoAssembler assembler;
        
    @Operation(
        summary = "Mostrar todos los métodos de pago", 
        description = "Muestra una lista de todos los métodos de pago registrados"
    )
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<MetodoPagoDTO>>> MostrarTodos() {
        List<EntityModel<MetodoPagoDTO>> metodos = metodoPagoService.obtenerTodos().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        
        if (metodos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
            metodos,
            linkTo(methodOn(MetodoPagoControllerV2.class).MostrarTodos()).withSelfRel()
        ));
    }

    @Operation(
        summary = "Buscar método de pago por ID",
        description = "Busca un método de pago según su ID"
    )
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<MetodoPagoDTO>> BuscarMetodoPagoId(@PathVariable Integer id) {
        try {
            MetodoPagoDTO dto = metodoPagoService.buscarPorId(id);
            if (dto == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(assembler.toModel(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<MetodoPagoDTO>> registrar(@Valid @RequestBody MetodoPago metodoPago) {
        try {
            MetodoPagoDTO dto = metodoPagoService.guardar(metodoPago);
            return ResponseEntity
                    .created(linkTo(methodOn(MetodoPagoControllerV2.class).BuscarMetodoPagoId(dto.getId())).toUri())
                    .body(assembler.toModel(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(
        summary = "Editar método de pago",
        description = "Edita el método de pago por un id específico"
    )
    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<MetodoPagoDTO>> updateMetodoPago(@PathVariable Integer id, @RequestBody MetodoPago metodoPago) {
        try {
            MetodoPago metodoActualizado = metodoPagoService.actualizar(id, metodoPago);
            MetodoPagoDTO dto = metodoPagoService.buscarPorId(metodoActualizado.getId());
            return ResponseEntity.ok(assembler.toModel(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
        summary = "Eliminar método de pago",
        description = "Elimina el método de pago por un id específico"
    )
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> deleteMetodoPago(@PathVariable Integer id){
        try {
            metodoPagoService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}