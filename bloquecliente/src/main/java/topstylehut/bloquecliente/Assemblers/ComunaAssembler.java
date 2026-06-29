package topstylehut.bloquecliente.Assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

import topstylehut.bloquecliente.ControllerV2.ComunaControllerV2;
import topstylehut.bloquecliente.DTO.ComunaDTO;
import topstylehut.bloquecliente.Model.Comuna;

@Component
public class ComunaAssembler implements RepresentationModelAssembler<ComunaDTO, EntityModel<ComunaDTO>> {

    @Override
    public EntityModel<ComunaDTO> toModel(ComunaDTO comuna) {
        return EntityModel.of(comuna,
            linkTo(methodOn(ComunaControllerV2.class).BuscarComunaId(comuna.getId())).withSelfRel(),
            linkTo(methodOn(ComunaControllerV2.class).MostrarTodos()).withRel("Comunas")
        );
    }
}