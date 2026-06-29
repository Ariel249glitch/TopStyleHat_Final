package com.example.BloqueGorro.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.BloqueGorro.ControllerV2.MarcaControllerV2;
import com.example.BloqueGorro.DTO.MarcaDTO;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class MarcaAssembler implements RepresentationModelAssembler<MarcaDTO, EntityModel<MarcaDTO>> {

    @Override
    public EntityModel<MarcaDTO> toModel(MarcaDTO marca) {
        return EntityModel.of(marca,
                linkTo(methodOn(MarcaControllerV2.class).BuscarMarcaId(marca.getId())).withSelfRel(),
                linkTo(methodOn(MarcaControllerV2.class).MostrarTodos()).withRel("Marca")
        );
    }

}
