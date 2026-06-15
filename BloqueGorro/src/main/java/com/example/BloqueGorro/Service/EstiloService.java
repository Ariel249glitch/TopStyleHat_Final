package com.example.BloqueGorro.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.BloqueGorro.DTO.EstiloDTO;
import com.example.BloqueGorro.Model.Estilo;
import com.example.BloqueGorro.Repository.EstiloRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class EstiloService {

    @Autowired
    private EstiloRepository estiloRepository;

    //Mostrar todos los Estilos 
    public List<EstiloDTO> MostrarTodas(){
        List<EstiloDTO> estilos = new ArrayList<>();
        for (Estilo estilo : estiloRepository.findAll()) {
            estilos.add(convertirADTO(estilo));
        }
        return estilos;
    }

    //Convertir DTO
    private EstiloDTO convertirADTO (Estilo estilo){
        EstiloDTO estiloDTO = new EstiloDTO();
        estiloDTO.setId(estilo.getId());
        estiloDTO.setNombre(estilo.getNombre());
        return estiloDTO;
    }

    //buscar por id
    public EstiloDTO buscarPorId(Integer id){
        Estilo estilo = estiloRepository.findById(id).orElseThrow(() -> new RuntimeException("Estilo no encontrada"));
        return convertirADTO(estilo);
    }

    //Guardar Estilo
    public EstiloDTO guardarEstilo(Estilo nuevoEstilo){
        Estilo estiloGuardado = estiloRepository.save(nuevoEstilo);
        return convertirADTO(estiloGuardado);
    }

    //Actualizar Estilo
    public Estilo actualizarEsti(Integer id, Estilo estilo){
        Estilo Esti = estiloRepository.findById(id).orElseThrow(() ->  new RuntimeException("El estilo no existe"));
        if (estilo.getNombre() != null) {
            Esti.setNombre(estilo.getNombre());
        }
        return estiloRepository.save(Esti);
    }

    //Eliminar
    public String EliminarEstilo(Integer id){
        try {
            Estilo estilo = estiloRepository.findById(id).orElseThrow(() -> new RuntimeException("No se puede eliminar El Estilo con id" + id + "No existe" ));
            estiloRepository.delete(estilo);
            return "el estilo '" + estilo.getNombre() + "' a sido eliminada";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }
}

