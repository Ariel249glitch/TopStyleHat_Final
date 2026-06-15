package com.example.BloqueGorro.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.BloqueGorro.DTO.MaterialesDTO;
import com.example.BloqueGorro.Model.Materiales;
import com.example.BloqueGorro.Repository.MaterialesRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class MaterialesService {

    @Autowired
    private MaterialesRepository materialesRepository;

    //Mostrar todos
    public List<MaterialesDTO> obtenerTodos(){
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
        MaterialesDTO.setNombre(materiales.getNombre());
        return MaterialesDTO;
    }

    //Buscar por Id
    public MaterialesDTO buscarPorId(Integer id){
        Materiales materiales = materialesRepository.findById(id).orElseThrow(() -> new RuntimeException( "Materiales no encontrados"));
        return convertirADTO(materiales);
    }

    //Guardar Materiales
    public MaterialesDTO guardarMateriales(Materiales nuevosMateriales){
        Materiales materialesGuardados = materialesRepository.save(nuevosMateriales);
        return convertirADTO(materialesGuardados);
    }

     //Actualizar Materiales
    public Materiales actualizarMateriales(Integer id, Materiales materiales){
        Materiales stuff = materialesRepository.findById(id).orElseThrow(() ->  new RuntimeException("no existe"));
        if (materiales.getNombre() != null) {
            stuff.setNombre(materiales.getNombre());
        }
        return materialesRepository.save(stuff);
    }

    //Eliminar Materiales
    public String eliminarMateriales(Integer id){
        try {
            Materiales materiales = materialesRepository.findById(id).orElseThrow(() ->  new RuntimeException(" no se puede eliminar el material con id" + id + "No existe"));
            materialesRepository.delete(materiales);
            return "los materiales '" + materiales.getNombre() + "' han sido eliminados";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

}

