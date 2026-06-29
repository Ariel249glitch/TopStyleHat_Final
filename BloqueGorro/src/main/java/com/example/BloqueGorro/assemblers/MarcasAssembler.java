package com.example.BloqueGorro.assemblers;


import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.BloqueGorro.ControllerV2.MarcasControllerV2;
import com.example.BloqueGorro.DTO.MarcasDTO;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class MarcasAssembler implements RepresentationModelAssembler<MarcasDTO, EntityModel<MarcasDTO>> {
    
    @Override
    public EntityModel<MarcasDTO> toModel(MarcasDTO marcas) {
        return EntityModel.of(marcas,
                linkTo(methodOn(MarcasControllerV2.class).BuscarMarcaId(marcas.getId())).withSelfRel(),
                linkTo(methodOn(MarcasControllerV2.class).MostrarTodos()).withRel("Marcas")
        );
    }
}
