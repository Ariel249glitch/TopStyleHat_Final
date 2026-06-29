package topstylehut.bloquecliente.Assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

import topstylehut.bloquecliente.ControllerV2.ClienteControllerV2;
import topstylehut.bloquecliente.DTO.ClienteDTO;

@Component
public class ClienteAssembler implements RepresentationModelAssembler<ClienteDTO, EntityModel<ClienteDTO>> {

    @Override
    public EntityModel<ClienteDTO> toModel(ClienteDTO cliente) {
        return EntityModel.of(cliente,
            linkTo(methodOn(ClienteControllerV2.class).BuscarClienteId(cliente.getId())).withSelfRel(),            
            linkTo(methodOn(ClienteControllerV2.class).MostrarTodos()).withRel("Clientes")         
        );
    }
}