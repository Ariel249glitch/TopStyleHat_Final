package topstylehut.bloquecliente.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired; // Import para los logs de Lombok
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import topstylehut.bloquecliente.DTO.ClienteDTO;
import topstylehut.bloquecliente.Model.Cliente;
import topstylehut.bloquecliente.Repository.ClienteRepository;

@Slf4j
@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<ClienteDTO> obtenerTodos() {
        log.info("Llamando al servicio para obtener la lista de todos los clientes");
        List<Cliente> clientes = clienteRepository.findAll();
        log.info("Se encontraron {} clientes en la base de datos", clientes.size());
        
        return clientes.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public ClienteDTO buscarPorId(Integer id) {
        log.info("Iniciando búsqueda de cliente con ID: {}", id);
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Error: No se encontró al cliente con ID: {}", id);
                    return new RuntimeException("Cliente no encontrado");
                });
        
        log.info("Cliente encontrado exitosamente: {}", cliente.getNombre());
        return convertirADTO(cliente);
    }

    public ClienteDTO guardar(Cliente cliente) {
        log.info("Insertando o actualizando un cliente en el sistema");
        Cliente clienteGuardado = clienteRepository.save(cliente);
        log.info("Cliente guardado con éxito. ID generado: {}", clienteGuardado.getId());
        
        return convertirADTO(clienteGuardado);
    }

    public String eliminar(Integer id) {
        log.info("Petición recibida para eliminar al cliente con ID: {}", id);
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Error al intentar eliminar: Cliente con ID {} no existe", id);
                    return new RuntimeException("Cliente no encontrado");
                });

        clienteRepository.delete(cliente);
        log.info("El cliente '{}' fue eliminado correctamente de la base de datos", cliente.getNombre());
        return "El cliente '" + cliente.getNombre() + "' ha sido eliminado exitosamente";
    }

    private ClienteDTO convertirADTO(Cliente cliente) {
        ClienteDTO dto = new ClienteDTO();
        dto.setId(cliente.getId());
        dto.setNombre(cliente.getNombre());
        dto.setDireccion(cliente.getDireccion()); 
        return dto;
    }
}