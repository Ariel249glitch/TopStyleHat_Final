package com.example.BloqueGorro.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.BloqueGorro.DTO.MarcasDTO;
import com.example.BloqueGorro.Model.Marcas;
import com.example.BloqueGorro.Repository.MarcasRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional

public class MarcasService {

    @Autowired
    private MarcasRepository marcasRepository;


    //Mostrar todas las marcas
    public List<MarcasDTO> MostrarTodas(){
        List<MarcasDTO> marcas = new ArrayList<>();
        for (Marcas marcas2 : marcasRepository.findAll()) {
            marcas.add(convertirADTO(marcas2));
        }
        return marcas;
    }

     //Convertir DTO
    private MarcasDTO convertirADTO (Marcas marcas){
        MarcasDTO marcasDTO = new MarcasDTO();
        marcasDTO.setId(marcas.getId());
        return marcasDTO;
    }

    //buscar por id
    public MarcasDTO buscarPorId(Integer id){
        Marcas marcas = marcasRepository.findById(id).orElseThrow(() -> new RuntimeException("no encontrada"));
        return convertirADTO(marcas);
    }

    //Guardar Marcas
    public MarcasDTO guardarMarcas(Marcas nuevasMarcas){
        Marcas marcasGuardadas = marcasRepository.save(nuevasMarcas);
        return convertirADTO(marcasGuardadas);
    }

    



}

