package com.example.BloqueGorro.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.example.BloqueGorro.ControllerV2.ColorControllerV2;
import com.example.BloqueGorro.DTO.ColorDTO;

@Component
public class ColorAssembler implements RepresentationModelAssembler<ColorDTO, EntityModel<ColorDTO>>{

    @Override
    public EntityModel<ColorDTO> toModel(ColorDTO Color) {
        return EntityModel.of(Color,
                linkTo(methodOn(ColorControllerV2.class).BuscarColorId(Color.getId())).withSelfRel(),
                linkTo(methodOn(ColorControllerV2.class).MostrarTodos()).withRel("Color")
        );
    }

}
