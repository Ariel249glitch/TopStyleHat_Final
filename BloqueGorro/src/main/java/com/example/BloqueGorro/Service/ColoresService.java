package com.example.BloqueGorro.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.BloqueGorro.DTO.ColoresDTO;
import com.example.BloqueGorro.Model.Color;
import com.example.BloqueGorro.Model.Colores;
import com.example.BloqueGorro.Model.Gorro;
import com.example.BloqueGorro.Repository.ColorRepository;
import com.example.BloqueGorro.Repository.ColoresRepository;
import com.example.BloqueGorro.Repository.GorroRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;




@Service
@Transactional
@Slf4j
public class ColoresService {

    @Autowired
    private ColoresRepository coloresRepository;

    @Autowired
    private GorroRepository gorroRepository;

    @Autowired
    private ColorRepository colorRepository;

    public List<ColoresDTO> obtenerTodas() {
        log.info("Obteniendo todas las relaciones de colores y gorros");
        List<ColoresDTO> relacionesDTO = new ArrayList<>();
        for (Colores relacion : coloresRepository.findAll()) {
            relacionesDTO.add(convertirADTO(relacion));
        }
        return relacionesDTO;
    }

    public ColoresDTO buscarPorId(Integer id) {
        log.info("Buscando relación de colores y gorros por ID: {}", id);
        Colores relacion = coloresRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Relación no encontrada"));
        return convertirADTO(relacion);
    }

    public ColoresDTO asignarColorAGorro(Integer gorroId, Integer colorId) {
        log.info("Asignando color con ID {} al gorro con ID {}", colorId, gorroId);
        Gorro gorro = gorroRepository.findById(gorroId)
            .orElseThrow(() -> new RuntimeException("Gorro no encontrado"));
        
        Color color = colorRepository.findById(colorId)
            .orElseThrow(() -> new RuntimeException("Color no encontrado"));
        
        Colores nuevaRelacion = new Colores();
        nuevaRelacion.setGorro(gorro);
        nuevaRelacion.setColor(color);
        
        Colores relacionGuardada = coloresRepository.save(nuevaRelacion);
        return convertirADTO(relacionGuardada);
    }

    public String eliminarRelacion(Integer id) {
        log.info("Eliminando relación de colores y gorros por ID: {}", id);
        try {
            Colores relacion = coloresRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Relación no existe"));
            coloresRepository.delete(relacion);
            return "Relación eliminada exitosamente";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    public List<ColoresDTO> buscarPorGorro(Integer gorroId) {
        log.info("Buscando relaciones de colores y gorros por ID de gorro: {}", gorroId);
        List<ColoresDTO> relacionesDTO = new ArrayList<>();
        for (Colores relacion : coloresRepository.findByGorroId(gorroId)) {
            relacionesDTO.add(convertirADTO(relacion));
        }
        return relacionesDTO;
    }

    public List<ColoresDTO> buscarPorColor(Integer colorId) {
        log.info("Buscando relaciones de colores y gorros por ID de color: {}", colorId);
        List<ColoresDTO> relacionesDTO = new ArrayList<>();
        for (Colores relacion : coloresRepository.findByColorId(colorId)) {
            relacionesDTO.add(convertirADTO(relacion));
        }
        return relacionesDTO;
    }

    private ColoresDTO convertirADTO(Colores relacion) {
        ColoresDTO dto = new ColoresDTO();
        dto.setId(relacion.getId());
        
        if (relacion.getGorro() != null) {
            dto.setNombreGorro(relacion.getGorro().getNombre());
        } else {
            dto.setNombreGorro("Desconocido");
        }
        
        if (relacion.getColor() != null) {
            dto.setNombreColor(relacion.getColor().getNombre());
        } else {
            dto.setNombreColor("Desconocido");
        }        
        return dto;
    }
}
