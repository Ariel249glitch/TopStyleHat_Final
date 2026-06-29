package com.example.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

import com.example.BloqueBoleta.ControllerV2.BoletaControllerV2;
import com.example.BloqueBoleta.DTO.BoletaDTO;
import com.example.BloqueBoleta.Model.Boleta;

@Component
public class BoletaControllerAssabler implements RepresentationModelAssembler<BoletaDTO, EntityModel<BoletaDTO>> {

    @Override
    public EntityModel<BoletaDTO> toModel(BoletaDTO boleta) {
        return EntityModel.of(boleta,
            linkTo(methodOn(BoletaControllerV2.class).BuscarBoletaId(boleta.getId())).withSelfRel(),
            linkTo(methodOn(BoletaControllerV2.class).MostrarTodos()).withRel("Boletas")
        );
    }
}
