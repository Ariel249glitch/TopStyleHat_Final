package topstylehut.bloquecliente.Assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

import topstylehut.bloquecliente.ControllerV2.RegionControllerV2;
import topstylehut.bloquecliente.DTO.RegionDTO;
import topstylehut.bloquecliente.Model.Region;

@Component
public class RegionAssembler implements RepresentationModelAssembler<RegionDTO, EntityModel<RegionDTO>> {

    @Override
    public EntityModel<RegionDTO> toModel(RegionDTO region) {
        return EntityModel.of(region,
            linkTo(methodOn(RegionControllerV2.class).BuscarRegionId(region.getId())).withSelfRel(),
            linkTo(methodOn(RegionControllerV2.class).MostrarTodos()).withRel("Regiones")
        );
    }
}