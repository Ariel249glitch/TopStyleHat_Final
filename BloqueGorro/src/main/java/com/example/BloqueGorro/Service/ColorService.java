package com.example.BloqueGorro.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.BloqueGorro.DTO.ColorDTO;
import com.example.BloqueGorro.Model.Color;
import com.example.BloqueGorro.Repository.ColorRepository;

import jakarta.transaction.Transactional;



@Service
@Transactional
public class ColorService {

    @Autowired
    private ColorRepository colorRepository;

    // Mostrar todos los colores
    public List<ColorDTO> obtenerTodos() {
        List<ColorDTO> coloresDTO = new ArrayList<>();
        for (Color color : colorRepository.findAll()) {
            coloresDTO.add(convertirADTO(color));
        }
        return coloresDTO;
    }

    // Buscar por ID
    public ColorDTO buscarPorId(Integer id) {
        Color color = colorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Color no encontrado"));
        return convertirADTO(color);
    }

    // Guardar color
    public Color guardar(Color color) {
        return colorRepository.save(color);
    }

    // Actualizar color
    public Color actualizar(Integer id, Color colorActualizado) {
        Color colorExistente = colorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Color no existe"));
        
        if (colorActualizado.getNombre() != null) {
            colorExistente.setNombre(colorActualizado.getNombre());
        }        
        return colorRepository.save(colorExistente);
    }

    // Eliminar color
    public String eliminar(Integer id) {
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
    private ColorDTO convertirADTO(Color color) {
        ColorDTO dto = new ColorDTO();
        dto.setId(color.getId());
        dto.setNombre(color.getNombre());
        return dto;
    }
}

