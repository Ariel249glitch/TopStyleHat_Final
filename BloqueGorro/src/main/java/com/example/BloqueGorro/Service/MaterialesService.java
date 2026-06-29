package com.example.BloqueGorro.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.BloqueGorro.DTO.MaterialesDTO;
import com.example.BloqueGorro.Model.Materiales;
import com.example.BloqueGorro.Repository.MaterialesRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class MaterialesService {

    @Autowired
    private MaterialesRepository materialesRepository;

    //Mostrar todos
    public List<MaterialesDTO> obtenerTodos(){
        log.info("Obteniendo todos los materiales");
        List<MaterialesDTO> materiales = new ArrayList<>(); 
        for (Materiales materiales2 : materialesRepository.findAll()){
            materiales.add(convertirADTO(materiales2));
        }
        return materiales;
    }

    //Convertir DTO
    private MaterialesDTO convertirADTO(Materiales materiales){
        MaterialesDTO MaterialesDTO = new MaterialesDTO();
        MaterialesDTO.setId(materiales.getId());
        return MaterialesDTO;
    }

    //Buscar por Id
    public MaterialesDTO buscarPorId(Integer id){
        log.info("Buscando materiales por ID: {}", id);
        Materiales materiales = materialesRepository.findById(id).orElseThrow(() -> new RuntimeException( "Materiales no encontrados"));
        return convertirADTO(materiales);
    }

    //Guardar Materiales
    public MaterialesDTO guardarMateriales(Materiales nuevosMateriales){
        log.info("Guardando nuevos materiales");
        Materiales materialesGuardados = materialesRepository.save(nuevosMateriales);
        return convertirADTO(materialesGuardados);
    }

    //Eliminar Materiales
    public String eliminarMateriales(Integer id){
        log.info("Eliminando materiales con ID: {}", id);
        try {
            Materiales materiales = materialesRepository.findById(id).orElseThrow(() ->  new RuntimeException(" no se puede eliminar el material con id" + id + "No existe"));
            materialesRepository.delete(materiales);
            return "los materiales han sido eliminados";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

}

