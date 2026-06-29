package com.example.BloqueGorro.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.BloqueGorro.DTO.EstiloDTO;
import com.example.BloqueGorro.Model.Estilo;
import com.example.BloqueGorro.Repository.EstiloRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;


@Service
@Transactional
@Slf4j
public class EstiloService {

    @Autowired
    private EstiloRepository estiloRepository;

    //Mostrar todos los Estilos 
    public List<EstiloDTO> MostrarTodas(){
        log.info("Obteniendo todos los estilos");
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
        log.info("Buscando estilo por ID: {}", id);
        Estilo estilo = estiloRepository.findById(id).orElseThrow(() -> new RuntimeException("Estilo no encontrado"));
        return convertirADTO(estilo);
    }

    // Guardar Estilo
    public EstiloDTO guardarEstilo(EstiloDTO estiloDTO) {
        log.info("Guardando estilo: {}", estiloDTO.getNombre());
        Estilo estilo = new Estilo();
        estilo.setNombre(estiloDTO.getNombre());
        Estilo estiloGuardado = estiloRepository.save(estilo);
        return convertirADTO(estiloGuardado);
    }

    


    //Actualizar Estilo
    public EstiloDTO actualizarEsti(Integer id, EstiloDTO estilo){
        log.info("Actualizando estilo por ID: {}", id);

        Estilo esti = estiloRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El estilo no existe"));
        if (estilo.getNombre() != null) {
            esti.setNombre(estilo.getNombre());
        }
        Estilo actualizado = estiloRepository.save(esti);
        return convertirADTO(actualizado);
    }

    //Eliminar
    public String EliminarEstilo(Integer id){
        log.info("Eliminando estilo por ID: {}", id);
        try {
            Estilo estilo = estiloRepository.findById(id).orElseThrow(() -> new RuntimeException("No se puede eliminar El Estilo con id" + id + "No existe" ));
            estiloRepository.delete(estilo);
            return "el estilo '" + estilo.getNombre() + "' a sido eliminada";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }
}

