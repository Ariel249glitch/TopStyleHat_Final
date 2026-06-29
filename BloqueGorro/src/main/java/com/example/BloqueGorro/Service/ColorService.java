package com.example.BloqueGorro.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.BloqueGorro.DTO.ColorDTO;
import com.example.BloqueGorro.Model.Color;
import com.example.BloqueGorro.Repository.ColorRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;



@Service
@Transactional
@Slf4j
public class ColorService {

    @Autowired
    private ColorRepository colorRepository;

    // Mostrar todos los colores
    public List<ColorDTO> obtenerTodos() {
        log.info("Obteniendo todos los colores");
        List<ColorDTO> coloresDTO = new ArrayList<>();
        for (Color color : colorRepository.findAll()) {
            coloresDTO.add(convertirADTO(color));
        }
        return coloresDTO;
    }

    // Buscar por ID
    public ColorDTO buscarPorId(Integer id) {
        log.info("Buscando color por ID: {}", id);
        Color color = colorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Color no encontrado"));
        return convertirADTO(color);
    }

    // Guardar color
    public ColorDTO guardar(Color color) {
        log.info("Guardando color: {}", color.getNombre());
        Color nuevoColor = colorRepository.save(color);
        return convertirADTO(nuevoColor);
    }

    // Actualizar color
    public Color actualizar(Integer id, Color colorActualizado) {
        log.info("Actualizando color por ID: {}", id);
        Color colorExistente = colorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Color no existe"));
        
        if (colorActualizado.getNombre() != null) {
            colorExistente.setNombre(colorActualizado.getNombre());
        }        
        return colorRepository.save(colorExistente);
    }

    // Eliminar color
    public String eliminar(Integer id) {
        log.info("Eliminando color por ID: {}", id);
        try {
            Color color = colorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Color no existe"));
            colorRepository.delete(color);
            return "El color '" + color.getNombre() + "' ha sido eliminado exitosamente";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    // Convertir Entity a DTO
    public ColorDTO convertirADTO(Color color) {
        ColorDTO dto = new ColorDTO();
        dto.setId(color.getId());
        dto.setNombre(color.getNombre());
        return dto;
    }
}

