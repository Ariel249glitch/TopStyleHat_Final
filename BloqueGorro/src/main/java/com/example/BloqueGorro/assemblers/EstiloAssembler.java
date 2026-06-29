package com.example.BloqueGorro.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.BloqueGorro.ControllerV2.EstiloControllerV2;
import com.example.BloqueGorro.DTO.EstiloDTO;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class EstiloAssembler implements RepresentationModelAssembler<EstiloDTO, EntityModel<EstiloDTO>> {

    @Override
    public EntityModel<EstiloDTO> toModel(EstiloDTO estilo) {
        return EntityModel.of(estilo,
                linkTo(methodOn(EstiloControllerV2.class).BuscarEstiloId(estilo.getId())).withSelfRel(),
                linkTo(methodOn(EstiloControllerV2.class).MostrarTodos()).withRel("Estilo")
        );
    }

}
