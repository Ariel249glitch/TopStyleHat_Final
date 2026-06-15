package com.example.BloqueGorro.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.BloqueGorro.DTO.GorrosDTO;
import com.example.BloqueGorro.Model.Gorros;
import com.example.BloqueGorro.Repository.GorrosRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class GorrosService {

    @Autowired
    private GorrosRepository gorrosRepository;

    //Mostrar todos
    public List<GorrosDTO> obtenerTodos(){
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
        gorrosDTO.setNombre(gorros.getNombre());
        gorrosDTO.setTalla(gorros.getTalla());
        gorrosDTO.setPrecio(gorros.getPrecio());
        return gorrosDTO;
    }

    //Buscar por id
    public GorrosDTO buscarPorId(Integer id){
        Gorros gorros = gorrosRepository.findById(id).orElseThrow(() -> new RuntimeException( "Gorro no encontrado"));
        return convertirADTO(gorros);
    }

    //Guardar Gorro
    public GorrosDTO guardarGorros (Gorros NuevosGorros){
        Gorros gorrosGuardados = gorrosRepository.save(NuevosGorros);
        return convertirADTO(gorrosGuardados);
    }

    //Actualizar
    public Gorros ActualizarGorros (Integer id, Gorros gorros){
        Gorros hats = gorrosRepository.findById(id).orElseThrow(() -> new RuntimeException("no existe!"));
        if (gorros.getNombre() != null){
            hats.setNombre(gorros.getNombre());
        }
        if (gorros.getTalla() != null){
            hats.setTalla(gorros.getTalla());
        }
        if (gorros.getPrecio() != null){
            hats.setPrecio(gorros.getPrecio());
        }
        return gorrosRepository.save(hats);
    }

    //Eliminar
    public String Eliminar(Integer id){
        try {
            Gorros gorros = gorrosRepository.findById(id).orElseThrow(() -> new RuntimeException("!Imposible de eliminar! El gorro con id" + id +"No existe"));
            gorrosRepository.delete(gorros);
            return "El gorro '" + gorros.getNombre() + "' ha sido eliminado";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    

}

