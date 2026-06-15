package com.example.BloqueBoleta.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.BloqueBoleta.DTO.BoletaDTO;
import com.example.BloqueBoleta.Model.Boleta;
import com.example.BloqueBoleta.Repository.BoletaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class BoletaService {

    @Autowired
    private BoletaRepository boletaRepository;

    @Autowired
    private BoletaValidaciones boletaValidaciones;

    

    // Mostrar todas las boletas
    public List<BoletaDTO> obtenerTodas() {

        List<BoletaDTO> boletasDTO = new ArrayList<>();

        for (Boleta boleta : boletaRepository.findAll()) {
            boletasDTO.add(convertirADTO(boleta));
        }

        return boletasDTO;
    }

    // Buscar por ID
    public BoletaDTO buscarPorId(Integer id) {

        Boleta boleta = boletaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Boleta no encontrada"));

        return convertirADTO(boleta);
    }

    // Guardar boleta
    public Boleta guardar(Boleta boleta) {
        return boletaRepository.save(boleta);
    }

    // Actualizar boleta
    public Boleta actualizar(Integer id, Boleta boletaActualizada) {

        Boleta boletaExistente = boletaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Boleta no existe"));

        if (boletaActualizada.getMetodoPago() != null) {
            boletaExistente.setMetodoPago(
                    boletaActualizada.getMetodoPago());
        }

        if (boletaActualizada.getMetodoEnvio() != null) {
            boletaExistente.setMetodoEnvio(
                    boletaActualizada.getMetodoEnvio());
        }

        if (boletaActualizada.getFecha() != null) {
            boletaExistente.setFecha(
                    boletaActualizada.getFecha());
        }

        return boletaRepository.save(boletaExistente);
    }

    // Eliminar boleta
    public String eliminar(Integer id) {

        Boleta boleta = boletaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Boleta no existe"));

        boletaRepository.delete(boleta);

        return "La boleta #" + boleta.getId() +
                " ha sido eliminada exitosamente";
    }

    // Convertir Entity a DTO
    public BoletaDTO convertirADTO(Boleta boleta){

    BoletaDTO dto = new BoletaDTO();

    dto.setId(boleta.getId());
    dto.setFecha(boleta.getFecha());

    dto.setMetodoPago(
        boleta.getMetodoPago().getNombre()
    );

    dto.setMetodoEnvio(
        boleta.getMetodoEnvio().getNombre()
    );

    dto.setCliente(
        boletaValidaciones.obtenerCliente(
            boleta.getClienteId()
        )
    );

    dto.setGorro(
        boletaValidaciones.obtenerGorro(
            boleta.getGorroId()
        )
    );

    return dto;
}
}
