package com.example.BloqueBoleta.Service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.BloqueBoleta.DTO.BoletaDTO;
import com.example.BloqueBoleta.Model.Boleta;
import com.example.BloqueBoleta.Repository.BoletaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class BoletaService {

    private static final Logger log = LoggerFactory.getLogger(BoletaService.class);

    @Autowired
    private BoletaRepository boletaRepository;

    @Autowired
    private BoletaValidaciones boletaValidaciones;

    // Mostrar todas las boletas
    public List<BoletaDTO> obtenerTodas() {
        log.info("Obteniendo todas las boletas");
        List<BoletaDTO> boletasDTO = new ArrayList<>();
        for (Boleta boleta : boletaRepository.findAll()) {
            boletasDTO.add(convertirADTO(boleta));
        }
        log.info("Se obtuvieron {} boletas", boletasDTO.size());
        return boletasDTO;
    }

    // Buscar por ID
    public BoletaDTO buscarPorId(Integer id) {
        log.info("Buscando boleta con ID: {}", id);
        try {
            Boleta boleta = boletaRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Boleta no encontrada"));
            return convertirADTO(boleta);
        } catch (RuntimeException e) {
            log.error("Error al buscar boleta con ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    // Guardar boleta
    public Boleta guardar(Boleta boleta) {
        log.info("Guardando nueva boleta: {}", boleta);
        Boleta saved = boletaRepository.save(boleta);
        log.info("Boleta guardada con ID: {}", saved.getId());
        return saved;
    }

    // Actualizar boleta
    public Boleta actualizar(Integer id, Boleta boletaActualizada) {
        log.info("Actualizando boleta con ID: {}", id);
        try {
            Boleta boletaExistente = boletaRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Boleta no existe"));

            if (boletaActualizada.getMetodoPago() != null) {
                boletaExistente.setMetodoPago(boletaActualizada.getMetodoPago());
            }
            if (boletaActualizada.getMetodoEnvio() != null) {
                boletaExistente.setMetodoEnvio(boletaActualizada.getMetodoEnvio());
            }
            if (boletaActualizada.getFecha() != null) {
                boletaExistente.setFecha(boletaActualizada.getFecha());
            }

            Boleta updated = boletaRepository.save(boletaExistente);
            log.info("Boleta con ID {} actualizada correctamente", id);
            return updated;
        } catch (RuntimeException e) {
            log.error("Error al actualizar boleta con ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    // Eliminar boleta
    public String eliminar(Integer id) {
        log.info("Eliminando boleta con ID: {}", id);
        try {
            Boleta boleta = boletaRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Boleta no existe"));
            boletaRepository.delete(boleta);
            String mensaje = "La boleta #" + boleta.getId() + " ha sido eliminada exitosamente";
            log.info(mensaje);
            return mensaje;
        } catch (RuntimeException e) {
            log.error("Error al eliminar boleta con ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    // Convertir Entity a DTO
    public BoletaDTO convertirADTO(Boleta boleta) {
        log.debug("Convirtiendo boleta ID {} a DTO", boleta.getId());
        BoletaDTO dto = new BoletaDTO();
        dto.setId(boleta.getId());
        dto.setFecha(boleta.getFecha());
        dto.setMetodoPago(boleta.getMetodoPago().getNombre());
        dto.setMetodoEnvio(boleta.getMetodoEnvio().getNombre());
        dto.setCliente(boletaValidaciones.obtenerCliente(boleta.getClienteId()));
        dto.setGorro(boletaValidaciones.obtenerGorro(boleta.getGorroId()));
        return dto;
    }
}