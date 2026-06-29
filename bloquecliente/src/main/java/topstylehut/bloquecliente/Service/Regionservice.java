package topstylehut.bloquecliente.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired; // Import para los logs de Lombok
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import topstylehut.bloquecliente.DTO.RegionDTO;
import topstylehut.bloquecliente.Model.Region;
import topstylehut.bloquecliente.Repository.RegionRepository;

@Slf4j // Activa el uso de log.info y log.error automáticamente
@Service
public class Regionservice {

    @Autowired
    private RegionRepository regionRepository;

    public List<RegionDTO> obtenerTodas() {
        log.info("Llamando al servicio para obtener la lista de todas las regiones");
        List<Region> regiones = regionRepository.findAll();
        log.info("Se encontraron {} regiones en la base de datos", regiones.size());
        
        return regiones.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public RegionDTO buscarPorId(Integer id) {
        log.info("Iniciando búsqueda de región con ID: {}", id);
        Region region = regionRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Error: No se encontró la región con ID: {}", id);
                    return new RuntimeException("Región no encontrada");
                });
        
        log.info("Región encontrada exitosamente: {}", region.getNombre());
        return convertirADTO(region);
    }

    public RegionDTO guardar(Region region) {
        log.info("Insertando o actualizando una región en el sistema");
        Region regionGuardada = regionRepository.save(region);
        log.info("Región guardada con éxito. ID generado: {}", regionGuardada.getId());
        
        return convertirADTO(regionGuardada);
    }

    public String eliminar(Integer id) {
        log.info("Petición recibida para eliminar la región con ID: {}", id);
        Region region = regionRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Error al intentar eliminar: Región con ID {} no existe", id);
                    return new RuntimeException("Región no encontrada");
                });

        regionRepository.delete(region);
        log.info("La región '{}' fue eliminada correctamente de la base de datos", region.getNombre());
        return "La región '" + region.getNombre() + "' ha sido eliminado exitosamente";
    }

    // Método auxiliar para transformar la entidad en DTO sin campos extras
    private RegionDTO convertirADTO(Region region) {
        RegionDTO dto = new RegionDTO();
        dto.setId(region.getId());
        dto.setNombre(region.getNombre());
        return dto;
    }
}