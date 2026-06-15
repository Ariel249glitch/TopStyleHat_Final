package com.example.BloqueGorro.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.BloqueGorro.DTO.EstilosDTO;
import com.example.BloqueGorro.Model.Estilos;
import com.example.BloqueGorro.Repository.EstilosRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class EstilosService {

    @Autowired
    private EstilosRepository estilosRepository;

    //Mostrar todos los Estilos
    public List<EstilosDTO> MostrarTodas(){
        List<EstilosDTO> estiloss = new ArrayList<>();
        for (Estilos estilos : estilosRepository.findAll()) {
            estiloss.add(convertirADTO(estilos));
        }
        return estiloss;
    }

    //Convertir DTO
    private EstilosDTO convertirADTO (Estilos estilos){
        EstilosDTO estilosDTO = new EstilosDTO();
        estilosDTO.setId(estilos.getId());
        estilosDTO.setNombre(estilos.getNombre());
        return estilosDTO;
    }

    //buscar por id
    public EstilosDTO buscarPorId(Integer id){
        Estilos estilos = estilosRepository.findById(id).orElseThrow(() -> new RuntimeException("Estilo no encontrada"));
        return convertirADTO(estilos);
    }

    //Guardar Estilo
    public EstilosDTO guardarEstilos(Estilos nuevoEstilos){
        Estilos estilosGuardado = estilosRepository.save(nuevoEstilos);
        return convertirADTO(estilosGuardado);
    }

    //Actualizar Estilo
    public Estilos actualizarEstis(Integer id, Estilos estilos){
        Estilos Estis = estilosRepository.findById(id).orElseThrow(() ->  new RuntimeException("El estilos no existe"));
        if (estilos.getNombre() != null) {
            Estis.setNombre(estilos.getNombre());
        }
        return estilosRepository.save(Estis);
    }

    //Eliminar
    public String EliminarEstilos(Integer id){
        try {
            Estilos estilos = estilosRepository.findById(id).orElseThrow(() -> new RuntimeException("No se puede eliminar El Estilos con id" + id + "No existe" ));
            estilosRepository.delete(estilos);
            return "El estilos '" + estilos.getNombre() + "' a sido eliminado";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }
}
