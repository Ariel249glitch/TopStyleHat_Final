package com.example.BloqueBoleta.Service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.BloqueBoleta.DTO.MetodoEDTO;
import com.example.BloqueBoleta.Model.MetodoE;
import com.example.BloqueBoleta.Repository.MetodoERepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class MetodoEService {

    private static final Logger log = LoggerFactory.getLogger(MetodoEService.class);

    @Autowired
    private MetodoERepository metodoERepository;

    // Mostrar todos los tipos
    public List<MetodoEDTO> MostrarTodas() {
        log.info("Mostrando todos los métodos de entrega");
        List<MetodoEDTO> MetodoEs = new ArrayList<>();
        for (MetodoE metodoE : metodoERepository.findAll()) {
            MetodoEs.add(convertirADTO(metodoE));
        }
        log.info("Total de métodos de entrega: {}", MetodoEs.size());
        return MetodoEs;
    }

    // Convertir a DTO
    private MetodoEDTO convertirADTO(MetodoE metodoE) {
        MetodoEDTO metodoEDTO = new MetodoEDTO();
        metodoEDTO.setId(metodoE.getId());
        metodoEDTO.setNombre(metodoE.getNombre());
        metodoEDTO.setTiempo(metodoE.getTiempo());
        return metodoEDTO;
    }

    // buscar por id
    public MetodoEDTO buscarPorId(Integer id) {
        log.info("Buscando método de entrega con ID: {}", id);
        try {
            MetodoE metodoE = metodoERepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Metodo de entrega no encontrada"));
            return convertirADTO(metodoE);
        } catch (RuntimeException e) {
            log.error("Error al buscar método de entrega ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    // Guardar Region
    public MetodoEDTO guardarMetodoE(MetodoE nuevoMetodoE) {
        log.info("Guardando nuevo método de entrega: {}", nuevoMetodoE);
        MetodoE metodoEGuardada = metodoERepository.save(nuevoMetodoE);
        log.info("Método de entrega guardado con ID: {}", metodoEGuardada.getId());
        return convertirADTO(metodoEGuardada);
    }

    // Actualizar
    public MetodoE actualizarMetodoE(Integer id, MetodoE metodoE) {
        log.info("Actualizando método de entrega con ID: {}", id);
        try {
            MetodoE metodoEExistente = metodoERepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("El metodo de entrega no existe"));
            metodoEExistente.setNombre(metodoE.getNombre());
            metodoEExistente.setTiempo(metodoE.getTiempo());
            MetodoE updated = metodoERepository.save(metodoEExistente);
            log.info("Método de entrega con ID {} actualizado correctamente", id);
            return updated;
        } catch (RuntimeException e) {
            log.error("Error al actualizar método de entrega ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    // Eliminar
    public String EliminarMetodoE(Integer id) {
        log.info("Eliminando método de entrega con ID: {}", id);
        try {
            MetodoE metodoE = metodoERepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("El metodo de entrega no existe"));
            metodoERepository.delete(metodoE);
            String mensaje = "Metodo de entrega Eliminada";
            log.info(mensaje + " (ID: {})", id);
            return mensaje;
        } catch (RuntimeException e) {
            log.error("Error al eliminar método de entrega ID {}: {}", id, e.getMessage());
            throw e;
        }
    }
}