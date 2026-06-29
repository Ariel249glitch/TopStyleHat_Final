package com.example.BloqueGorro.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.BloqueGorro.ControllerV2.GorrosControllerV2;
import com.example.BloqueGorro.DTO.GorrosDTO;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class GorrosAssembler implements RepresentationModelAssembler<GorrosDTO, EntityModel<GorrosDTO>>{

    @Override
    public EntityModel<GorrosDTO> toModel(GorrosDTO gorros) {
        return EntityModel.of(gorros,
                linkTo(methodOn(GorrosControllerV2.class).BuscarGorrosId(gorros.getId())).withSelfRel(),
                linkTo(methodOn(GorrosControllerV2.class).MostrarTodos()).withRel("Gorros")
        );
    }


}
