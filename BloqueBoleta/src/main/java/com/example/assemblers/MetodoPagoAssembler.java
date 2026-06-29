package com.example.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

import com.example.BloqueBoleta.ControllerV2.MetodoPagoControllerV2;
import com.example.BloqueBoleta.DTO.MetodoPagoDTO;
import com.example.BloqueBoleta.Model.MetodoPago;

@Component
public class MetodoPagoAssembler implements RepresentationModelAssembler<MetodoPagoDTO, EntityModel<MetodoPagoDTO>> {

    @Override
    public EntityModel<MetodoPagoDTO> toModel(MetodoPagoDTO metodoPago) {
        return EntityModel.of(metodoPago,
            linkTo(methodOn(MetodoPagoControllerV2.class).BuscarMetodoPagoId(metodoPago.getId())).withSelfRel(),
            linkTo(methodOn(MetodoPagoControllerV2.class).MostrarTodos()).withRel("MetodosPago")
        );
    }
}