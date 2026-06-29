package com.example.BloqueGorro.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.BloqueGorro.ControllerV2.TipoControllerV2;
import com.example.BloqueGorro.DTO.TipoDTO;

@Component
public class TipoAssembler implements RepresentationModelAssembler<TipoDTO, EntityModel<TipoDTO>> {

    @Override
    public EntityModel<TipoDTO> toModel(TipoDTO tipo) {
        return EntityModel.of(tipo,
                linkTo(methodOn(TipoControllerV2.class).BuscarTipoId(tipo.getId())).withSelfRel(),
                linkTo(methodOn(TipoControllerV2.class).MostrarTodos()).withRel("Tipo")
        );
    }

}
