package com.example.BloqueGorro.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.BloqueGorro.DTO.GorroDTO;
import com.example.BloqueGorro.Model.Gorro;
import com.example.BloqueGorro.Repository.GorroRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class GorroService {

    @Autowired
    private GorroRepository gorroRepository;

    //Mostrar todos
    public List<GorroDTO> obtenerTodos(){
        log.info("Obteniendo todos los gorros");
        List<GorroDTO> gorros = new ArrayList<>();
        for (Gorro gorro : gorroRepository.findAll()){
            gorros.add(convertirADTO(gorro));
        }
        return gorros;
    }

    //Convertir a DTO
    private GorroDTO convertirADTO (Gorro gorro){
        GorroDTO gorroDTO = new GorroDTO();
        gorroDTO.setId(gorro.getId());
        gorroDTO.setNombre(gorro.getNombre());
        gorroDTO.setTalla(gorro.getTalla());
        gorroDTO.setPrecio(gorro.getPrecio());
        return gorroDTO;
    }

    //Buscar por id
    public GorroDTO buscarPorId(Integer id){
        log.info("Buscando gorro por ID: {}", id);
        Gorro gorro = gorroRepository.findById(id).orElseThrow(() -> new RuntimeException( "Gorro no encontrado"));
        return convertirADTO(gorro);
    }



    //Guardar Gorro
    public GorroDTO guardarGorro (Gorro NuevoGorro){
        log.info("Guardando nuevo gorro");
        Gorro gorroGuardado = gorroRepository.save(NuevoGorro);
        return convertirADTO(gorroGuardado);
    }

    //Actualizar
    public Gorro ActualizarGorro (Integer id, Gorro gorro){
        log.info("Actualizando gorro con ID: {}", id);
        Gorro hat = gorroRepository.findById(id).orElseThrow(() -> new RuntimeException("El gorro no existe!"));
        if (gorro.getNombre() != null){
            hat.setNombre(gorro.getNombre());
        }
        if (gorro.getTalla() != null){
            hat.setTalla(gorro.getTalla());
        }
        if (gorro.getPrecio() != null){
            hat.setPrecio(gorro.getPrecio());
        }
        return gorroRepository.save(hat);
    }

    //Eliminar
    public String Eliminar(Integer id){
        log.info("Eliminando gorro con ID: {}", id);
        try {
            Gorro gorro = gorroRepository.findById(id).orElseThrow(() -> new RuntimeException("!Imposible de eliminar! El gorro con id" + id +"No existe"));
            gorroRepository.delete(gorro);
            return "El gorro '" + gorro.getNombre() + "' ha sido eliminado";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    


}

