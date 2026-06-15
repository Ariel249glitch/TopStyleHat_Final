package com.example.BloqueGorro.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.BloqueGorro.DTO.MaterialDTO;
import com.example.BloqueGorro.Model.Material;
import com.example.BloqueGorro.Repository.MaterialRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class MaterialService {

    @Autowired
    MaterialRepository materialRepository;

    //Mostrar todos los materiales
    public List<MaterialDTO> mostrarTodos(){
        List<MaterialDTO> materiales = new ArrayList<>();
        for (Material material : materialRepository.findAll()) {
            materiales.add(convertirADTO(material));  
        }
        return materiales;
    }

    //Convertir DTO
    private MaterialDTO convertirADTO(Material material){
        MaterialDTO materialDTO = new MaterialDTO();
        materialDTO.setId(material.getId());
        materialDTO.setNombre(material.getNombre());
        return materialDTO;
    }

    //buscar por Id
    public MaterialDTO buscarPorId(Integer id){
        Material material = materialRepository.findById(id).orElseThrow(() -> new RuntimeException("Material no encontrado"));
        return convertirADTO(material);
    }

    //Guardar Material
    public MaterialDTO guardarMaterial(Material NuevoMaterial){
        Material materialGuardado = materialRepository.save(NuevoMaterial);
        return convertirADTO(materialGuardado);
    }

    //Actualizar Material
    public Material actualizarMaterial(Integer id, Material material){
        Material stuff = materialRepository.findById(id).orElseThrow(() ->  new RuntimeException("El material no existe"));
        if (material.getNombre() != null) {
            stuff.setNombre(material.getNombre());
        }
        return materialRepository.save(stuff);
    }

    //Eliminar Material
    public String eliminarMaterial(Integer id){
        try {
            Material material = materialRepository.findById(id).orElseThrow(() ->  new RuntimeException("no se puede eliminar el material con id" + id + "No existe"));
            materialRepository.delete(material);
            return "El material '" + material.getNombre() + "' a sido eliminado";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }






}

