package com.example.BloqueGorro.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.BloqueGorro.DTO.GorrosDTO;
import com.example.BloqueGorro.Model.Gorros;
import com.example.BloqueGorro.Repository.GorrosRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class GorrosService {

    @Autowired
    private GorrosRepository gorrosRepository;

    //Mostrar todos
    public List<GorrosDTO> obtenerTodos(){
        log.info("Obteniendo todos los gorros");
        List<GorrosDTO> hats = new ArrayList<>();
        for (Gorros gorros : gorrosRepository.findAll()){
            hats.add(convertirADTO(gorros));
        }
        return hats;
    }

    //Convertir a DTO
    private GorrosDTO convertirADTO (Gorros gorros){
        GorrosDTO gorrosDTO = new GorrosDTO();
        gorrosDTO.setId(gorros.getId());
        return gorrosDTO;
    }

    //Buscar por id
    public GorrosDTO buscarPorId(Integer id){
        log.info("Buscando gorro por ID: {}", id);
        Gorros gorros = gorrosRepository.findById(id).orElseThrow(() -> new RuntimeException( "Gorro no encontrado"));
        return convertirADTO(gorros);
    }

    //Guardar Gorro
    public GorrosDTO guardarGorros (Gorros NuevosGorros){
        log.info("Guardando nuevos gorros");
        Gorros gorrosGuardados = gorrosRepository.save(NuevosGorros);
        return convertirADTO(gorrosGuardados);
    }

    //Eliminar
    public String Eliminar(Integer id){
        log.info("Eliminando gorro con ID: {}", id);
        try {
            Gorros gorros = gorrosRepository.findById(id).orElseThrow(() -> new RuntimeException("!Imposible de eliminar! El gorro con id" + id +"No existe"));
            gorrosRepository.delete(gorros);
            return "El gorro ha sido eliminado";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    

}

