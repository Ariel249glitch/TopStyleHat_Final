package com.example.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

import com.example.BloqueBoleta.ControllerV2.MetodoEControllerV2;
import com.example.BloqueBoleta.DTO.MetodoEDTO;
import com.example.BloqueBoleta.Model.MetodoE;

@Component
public class MetodoEnvioAssembler implements RepresentationModelAssembler<MetodoEDTO, EntityModel<MetodoEDTO>> {

    @Override
    public EntityModel<MetodoEDTO> toModel(MetodoEDTO metodoE) {
        return EntityModel.of(metodoE,
            linkTo(methodOn(MetodoEControllerV2.class).B_M_E_Id(metodoE.getId())).withSelfRel(),
            linkTo(methodOn(MetodoEControllerV2.class).MostrarTodos()).withRel("MetodosEnvio")
        );
    }
}