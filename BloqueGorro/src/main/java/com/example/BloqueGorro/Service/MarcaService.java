package com.example.BloqueGorro.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.BloqueGorro.DTO.MarcaDTO;
import com.example.BloqueGorro.Model.Marca;
import com.example.BloqueGorro.Repository.MarcaRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class MarcaService {

    @Autowired
    private MarcaRepository marcaRepository;

    //Mostrar todas las marcas
    public List<MarcaDTO> MostrarTodas(){
        log.info("Obteniendo todas las marcas");
        List<MarcaDTO> marcas = new ArrayList<>();
        for (Marca marca : marcaRepository.findAll()) {
            marcas.add(convertirADTO(marca));
        }
        return marcas;
    }

    //Convertir DTO
    public MarcaDTO convertirADTO (Marca marca){
        MarcaDTO marcaDTO = new MarcaDTO();
        marcaDTO.setId(marca.getId());
        marcaDTO.setNombre(marca.getNombre());
        return marcaDTO;
    }

    //buscar por id
    public MarcaDTO buscarPorId(Integer id){
        log.info("Buscando marca por ID: {}", id);
        Marca marca = marcaRepository.findById(id).orElseThrow(() -> new RuntimeException("Marca no encontrada"));
        return convertirADTO(marca);
    }

    //Guardar Marca
    public MarcaDTO guardarMarca(Marca nuevaMarca){
        log.info("Guardando nueva marca");
        Marca marcaGuardada = marcaRepository.save(nuevaMarca);
        return convertirADTO(marcaGuardada);
    }

    //Actualizar Marca
    public Marca actualizarMarca(Integer id, Marca marca){
        log.info("Actualizando marca con ID: {}", id);
        Marca Brand = marcaRepository.findById(id).orElseThrow(() ->  new RuntimeException("La marca no existe"));
        if (marca.getNombre() != null) {
            Brand.setNombre(marca.getNombre());
        }
        return marcaRepository.save(Brand);
    }

    //Eliminar
    public String EliminarMarca(Integer id){
        log.info("Eliminando marca con ID: {}", id);
        try {
            Marca marca = marcaRepository.findById(id).orElseThrow(() -> new RuntimeException("No se puede eliminar la marca con id" + id + "No existe" ));
            marcaRepository.delete(marca);
            return "La marca '" + marca.getNombre() + "' a sido eliminada";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }





}

