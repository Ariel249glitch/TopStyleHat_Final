package com.example.BloqueGorro.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


import com.example.BloqueGorro.ControllerV2.MaterialesControllerV2;
import com.example.BloqueGorro.DTO.MaterialesDTO;

@Component
public class MaterialesAssembler implements RepresentationModelAssembler<MaterialesDTO, EntityModel<MaterialesDTO>>{
    @Override
    public EntityModel<MaterialesDTO> toModel(MaterialesDTO materiales) {
        return EntityModel.of(materiales,
                linkTo(methodOn(MaterialesControllerV2.class).BuscarMaterialesId(materiales.getId())).withSelfRel(),
                linkTo(methodOn(MaterialesControllerV2.class).MostrarTodos()).withRel("Materiales")
        );
    }

}
