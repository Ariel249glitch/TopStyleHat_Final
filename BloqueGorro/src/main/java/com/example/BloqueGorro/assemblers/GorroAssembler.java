package com.example.BloqueGorro.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.BloqueGorro.ControllerV2.GorroControllerV2;
import com.example.BloqueGorro.DTO.GorroDTO;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Component
public class GorroAssembler implements RepresentationModelAssembler<GorroDTO, EntityModel<GorroDTO>> {

    @Override
    public EntityModel<GorroDTO> toModel(GorroDTO gorro) {
        return EntityModel.of(gorro,
                linkTo(methodOn(GorroControllerV2.class).BuscarGorroId(gorro.getId())).withSelfRel(),
                linkTo(methodOn(GorroControllerV2.class).MostrarTodos()).withRel("Gorros")
        );
    }

}
