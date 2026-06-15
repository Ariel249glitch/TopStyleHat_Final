package topstylehut.bloquecliente.Service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import topstylehut.bloquecliente.DTO.ClienteDTO;
import topstylehut.bloquecliente.Model.Cliente;
import topstylehut.bloquecliente.Repository.ClienteRepository;

@Service
@Transactional
public class ClienteService {


@Autowired
private ClienteRepository clienteRepository;

// Mostrar todos los clientes
public List<ClienteDTO> obtenerTodos() {

    List<ClienteDTO> clientesDTO = new ArrayList<>();

    for (Cliente cliente : clienteRepository.findAll()) {
        clientesDTO.add(convertirADTO(cliente));
    }

    return clientesDTO;
}

// Buscar por ID
public ClienteDTO buscarPorId(Integer id) {

    Cliente cliente = clienteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

    return convertirADTO(cliente);
}

// Guardar cliente
public Cliente guardar(Cliente cliente) {
    return clienteRepository.save(cliente);
}

// Actualizar cliente
public Cliente actualizar(Integer id, Cliente clienteActualizado) {

    Cliente clienteExistente = clienteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Cliente no existe"));

    if (clienteActualizado.getNombre() != null) {
        clienteExistente.setNombre(clienteActualizado.getNombre());
    }

    if (clienteActualizado.getDireccion() != null) {
        clienteExistente.setDireccion(clienteActualizado.getDireccion());
    }

    if (clienteActualizado.getComuna() != null) {
        clienteExistente.setComuna(clienteActualizado.getComuna());
    }

    return clienteRepository.save(clienteExistente);
}

// Eliminar cliente
public String eliminar(Integer id) {

    Cliente cliente = clienteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Cliente no existe"));

    clienteRepository.delete(cliente);

    return "El cliente '" + cliente.getNombre() + "' ha sido eliminado exitosamente";
}

// Convertir Entity a DTO
public ClienteDTO convertirADTO(Cliente cliente) {

    ClienteDTO dto = new ClienteDTO();

    dto.setId(cliente.getId());
    dto.setNombre(cliente.getNombre());
    dto.setDireccion(cliente.getDireccion());

    if (cliente.getComuna() != null) {

        dto.setComuna(cliente.getComuna().getNombre());

        if (cliente.getComuna().getRegion() != null) {
            dto.setRegion(
                    cliente.getComuna()
                            .getRegion()
                            .getNombre()
            );
        }
    }

    return dto;
}


}
