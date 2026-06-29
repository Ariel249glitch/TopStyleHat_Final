package com.example.BloqueGorro.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.BloqueGorro.DTO.EstilosDTO;
import com.example.BloqueGorro.Model.Estilos;
import com.example.BloqueGorro.Repository.EstilosRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class EstilosService {

    @Autowired
    private EstilosRepository estilosRepository;

    //Mostrar todos los Estilos
    public List<EstilosDTO> MostrarTodas(){
        log.info("Obteniendo todos los estilos");
        List<EstilosDTO> estiloss = new ArrayList<>();
        for (Estilos estilos : estilosRepository.findAll()) {
            estiloss.add(convertirADTO(estilos));
        }
        return estiloss;
    }

    //Convertir DTO
    public EstilosDTO convertirADTO (Estilos estilos){
        EstilosDTO estilosDTO = new EstilosDTO();
        estilosDTO.setId(estilos.getId());

        return estilosDTO;
    }

    //buscar por id
    public EstilosDTO buscarPorId(Integer id){
        log.info("Buscando estilo por ID: {}", id);
        Estilos estilos = estilosRepository.findById(id).orElseThrow(() -> new RuntimeException("Estilo no encontrado"));
        return convertirADTO(estilos);
    }

    //Guardar Estilo
    public EstilosDTO guardarEstilos(Estilos nuevoEstilos){
        log.info("Guardando nuevo estilo");
        Estilos estilosGuardado = estilosRepository.save(nuevoEstilos);
        return convertirADTO(estilosGuardado);
    }

    //agregar dto
    public EstilosDTO saveDTO(EstilosDTO estilosDTO){
        log.info("Guardando nuevo estilo desde DTO");
        Estilos estilos = new Estilos();
        estilos.setId(estilosDTO.getId());
        Estilos estilosGuardado = estilosRepository.save(estilos);
        return convertirADTO(estilosGuardado);
    }


    //Eliminar
    public String EliminarEstilos(Integer id){
        log.info("Eliminando estilo con ID: {}", id);
        try {
            Estilos estilos = estilosRepository.findById(id).orElseThrow(() -> new RuntimeException("No se puede eliminar El Estilos con id" + id + "No existe" ));
            estilosRepository.delete(estilos);
            return "El estilo a sido eliminado";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }
}
