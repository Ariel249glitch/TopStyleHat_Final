package com.example.BloqueBoleta.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.BloqueBoleta.DTO.MetodoEDTO;
import com.example.BloqueBoleta.Model.MetodoE;
import com.example.BloqueBoleta.Repository.MetodoERepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class MetodoEService {
    
    @Autowired
    private MetodoERepository metodoERepository;

    //Mostrar todos los tipos 
    public List<MetodoEDTO> MostrarTodas(){
        List<MetodoEDTO> MetodoEs = new ArrayList<>();
        for (MetodoE metodoE : metodoERepository.findAll()) {
            MetodoEs.add(convertirADTO(metodoE));
        }
        return MetodoEs;
    }

    //Convertir a DTO
    private MetodoEDTO convertirADTO(MetodoE metodoE){
        MetodoEDTO metodoEDTO = new MetodoEDTO();
        metodoEDTO.setId(metodoE.getId());
        metodoEDTO.setNombre(metodoE.getNombre());
        metodoEDTO.setTiempo(metodoE.getTiempo());
        return metodoEDTO;
    }

    //buscar por id
    public MetodoEDTO buscarPorId(Integer id){
        MetodoE metodoE = metodoERepository.findById(id).orElseThrow(() -> new RuntimeException("Metodo de entrega no encontrada"));
        return convertirADTO(metodoE);
    }

    //Guardar Region
    public MetodoEDTO guardarMetodoE(MetodoE nuevoMetodoE){
        MetodoE metodoEGuardada = metodoERepository.save(nuevoMetodoE);
        return convertirADTO(metodoEGuardada);
    }

    //Actualizar
    public MetodoE actualizarMetodoE(Integer id, MetodoE metodoE){
        MetodoE metodoEExistente = metodoERepository.findById(id).orElseThrow(() -> new RuntimeException("El metodo de entrega no existe"));
        metodoEExistente.setNombre(metodoE.getNombre());
        metodoEExistente.setTiempo(metodoE.getTiempo());
        return metodoERepository.save(metodoEExistente);
    }

    //Eliminar
    public String EliminarMetodoE(Integer id){
        MetodoE metodoE = metodoERepository.findById(id).orElseThrow(() -> new RuntimeException("El metodo de entrega no existe"));
        metodoERepository.delete(metodoE);
        return "Metodo de entrega Eliminada";
    }
}
