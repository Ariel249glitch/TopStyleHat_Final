package com.example.BloqueGorro.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.BloqueGorro.ControllerV2.MaterialControllerV2;
import com.example.BloqueGorro.DTO.MaterialDTO;

@Component
public class MaterialAssembler implements RepresentationModelAssembler<MaterialDTO, EntityModel<MaterialDTO>>{
    @Override
    public EntityModel<MaterialDTO> toModel(MaterialDTO material) {
        return EntityModel.of(material,
                linkTo(methodOn(MaterialControllerV2.class).BuscarMaterialId(material.getId())).withSelfRel(),
                linkTo(methodOn(MaterialControllerV2.class).MostrarTodos()).withRel("Material")
        );
    }

}
