package com.example.BloqueGorro.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.example.BloqueGorro.ControllerV2.EstiloControllerV2;
import com.example.BloqueGorro.DTO.EstilosDTO;

@Component
public class EstilosAssembler implements RepresentationModelAssembler<EstilosDTO, EntityModel<EstilosDTO>>{
    
    @Override
    public EntityModel<EstilosDTO> toModel(EstilosDTO estilos) {
        return EntityModel.of(estilos,
            linkTo(methodOn(EstiloControllerV2.class).BuscarEstiloId(estilos.getId())).withSelfRel(),
            linkTo(methodOn(EstiloControllerV2.class).MostrarTodos()).withRel("Estilo")
        );

    }

}
