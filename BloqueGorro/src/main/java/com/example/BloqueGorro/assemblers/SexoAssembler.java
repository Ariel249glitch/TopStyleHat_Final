package com.example.BloqueGorro.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


import com.example.BloqueGorro.ControllerV2.SexoControllerV2;

import com.example.BloqueGorro.DTO.SexoDTO;

@Component
public class SexoAssembler implements RepresentationModelAssembler<SexoDTO, EntityModel<SexoDTO>> {
    @Override
    public EntityModel<SexoDTO> toModel(SexoDTO sexo) {
        return EntityModel.of(sexo,
                linkTo(methodOn(SexoControllerV2.class).BuscarSexoId(sexo.getId())).withSelfRel(),
                linkTo(methodOn(SexoControllerV2.class).MostrarTodos()).withRel("Sexo")
        );
    }

}
