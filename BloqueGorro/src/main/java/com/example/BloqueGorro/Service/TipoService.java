package com.example.BloqueGorro.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.BloqueGorro.DTO.TipoDTO;
import com.example.BloqueGorro.Model.Tipo;
import com.example.BloqueGorro.Repository.TipoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional

public class TipoService {

    @Autowired
    private TipoRepository tipoRepository;

    //Mostrar todos los tipos 
    public List<TipoDTO> MostrarTodas(){
        List<TipoDTO> tipos = new ArrayList<>();
        for (Tipo tipo : tipoRepository.findAll()) {
            tipos.add(convertirADTO(tipo));
        }
        return tipos;
    }

    //Convertir DTO
    private TipoDTO convertirADTO (Tipo tipo){
        TipoDTO tipoDTO = new TipoDTO();
        tipoDTO.setId(tipo.getId());
        tipoDTO.setNombre(tipo.getNombre());
        return tipoDTO;
    }

    //buscar por id
    public TipoDTO buscarPorId(Integer id){
        Tipo tipo = tipoRepository.findById(id).orElseThrow(() -> new RuntimeException("Tipo no encontrada"));
        return convertirADTO(tipo);
    }

    //Guardar Tipo
    public TipoDTO guardarTipo(Tipo nuevoTipo){
        Tipo tipoGuardada = tipoRepository.save(nuevoTipo);
        return convertirADTO(tipoGuardada);
    }

    //Actualizar Tipo
    public Tipo actualizarTipo(Integer id, Tipo tipo){
        Tipo Tipe = tipoRepository.findById(id).orElseThrow(() ->  new RuntimeException("El tipo de material no existe"));
        if (tipo.getNombre() != null) {
            Tipe.setNombre(tipo.getNombre());
        }
        return tipoRepository.save(Tipe);
    }

    //Eliminar
    public String EliminarTipo(Integer id){
        try {
            Tipo tipo = tipoRepository.findById(id).orElseThrow(() -> new RuntimeException("No se puede eliminar El tipo con id" + id + "No existe" ));
            tipoRepository.delete(tipo);
            return "El tipo '" + tipo.getNombre() + "' a sido eliminada";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }
}

