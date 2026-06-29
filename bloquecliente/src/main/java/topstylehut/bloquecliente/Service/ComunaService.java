package topstylehut.bloquecliente.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired; // Import para los logs de Lombok
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import topstylehut.bloquecliente.DTO.ComunaDTO;
import topstylehut.bloquecliente.Model.Comuna;
import topstylehut.bloquecliente.Repository.ComunaRepository;

@Slf4j
@Service
public class ComunaService {

    @Autowired
    private ComunaRepository comunaRepository;

    public List<ComunaDTO> obtenerTodas() {
        log.info("Llamando al servicio para obtener la lista de todas las comunas");
        List<Comuna> comunas = comunaRepository.findAll();
        log.info("Se encontraron {} comunas en la base de datos", comunas.size());
        
        return comunas.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public ComunaDTO buscarPorId(Integer id) {
        log.info("Iniciando búsqueda de comuna con ID: {}", id);
        Comuna comuna = comunaRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Error: No se encontró la comuna con ID: {}", id);
                    return new RuntimeException("Comuna no encontrada");
                });
        
        log.info("Comuna encontrada exitosamente: {}", comuna.getNombre());
        return convertirADTO(comuna);
    }

    public ComunaDTO guardar(Comuna comuna) {
        log.info("Insertando o actualizando una comuna en el sistema");
        Comuna comunaGuardada = comunaRepository.save(comuna);
        log.info("Comuna guardada con éxito. ID generado: {}", comunaGuardada.getId());
        
        return convertirADTO(comunaGuardada);
    }

    public String eliminar(Integer id) {
        log.info("Petición recibida para eliminar la comuna con ID: {}", id);
        Comuna comuna = comunaRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Error al intentar eliminar: Comuna con ID {} no existe", id);
                    return new RuntimeException("Comuna no encontrada");
                });

        comunaRepository.delete(comuna);
        log.info("La comuna '{}' fue eliminada correctamente de la base de datos", comuna.getNombre());
        return "La comuna '" + comuna.getNombre() + "' ha sido eliminado exitosamente";
    }

    private ComunaDTO convertirADTO(Comuna comuna) {
        ComunaDTO dto = new ComunaDTO();
        dto.setId(comuna.getId());
        dto.setNombre(comuna.getNombre());
        return dto;
    }
}