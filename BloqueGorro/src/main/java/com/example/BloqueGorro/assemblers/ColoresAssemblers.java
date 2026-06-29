package com.example.BloqueGorro.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.BloqueGorro.ControllerV2.ColoresControllerV2;
import com.example.BloqueGorro.DTO.ColoresDTO;

@Component
public class ColoresAssemblers implements RepresentationModelAssembler<ColoresDTO, EntityModel<ColoresDTO>>{
    
    @Override
    public EntityModel<ColoresDTO> toModel(ColoresDTO colores) {
        return EntityModel.of(colores,
                linkTo(methodOn(ColoresControllerV2.class).BuscarColoresPorId(colores.getId())).withSelfRel(),
                linkTo(methodOn(ColoresControllerV2.class).MostrarTodos()).withRel("Colores")
        );
    }

}
