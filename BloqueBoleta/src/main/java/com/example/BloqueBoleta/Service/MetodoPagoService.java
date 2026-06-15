package com.example.BloqueBoleta.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.BloqueBoleta.DTO.MetodoPagoDTO;
import com.example.BloqueBoleta.Model.MetodoPago;
import com.example.BloqueBoleta.Repository.MetodoPagoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class MetodoPagoService {

    @Autowired
    private MetodoPagoRepository metodoPagoRepository;

    // Mostrar todos
    public List<MetodoPagoDTO> obtenerTodos() {

        List<MetodoPagoDTO> lista = new ArrayList<>();

        for (MetodoPago metodoPago : metodoPagoRepository.findAll()) {
            lista.add(convertirADTO(metodoPago));
        }

        return lista;
    }

    // Buscar por ID
    public MetodoPagoDTO buscarPorId(Integer id) {

        MetodoPago metodoPago = metodoPagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Método de pago no encontrado"));

        return convertirADTO(metodoPago);
    }

    // Guardar
    public MetodoPago guardar(MetodoPago metodoPago) {
        return metodoPagoRepository.save(metodoPago);
    }

    // Actualizar
    public MetodoPago actualizar(Integer id, MetodoPago metodoPagoActualizado) {

        MetodoPago metodoPagoExistente = metodoPagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Método de pago no existe"));

        if (metodoPagoActualizado.getNombre() != null) {
            metodoPagoExistente.setNombre(
                    metodoPagoActualizado.getNombre());
        }

        return metodoPagoRepository.save(metodoPagoExistente);
    }

    // Eliminar
    public String eliminar(Integer id) {

        MetodoPago metodoPago = metodoPagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Método de pago no existe"));

        metodoPagoRepository.delete(metodoPago);

        return "El método de pago '" +
                metodoPago.getNombre() +
                "' ha sido eliminado exitosamente";
    }

    // Convertir Entity a DTO
    public MetodoPagoDTO convertirADTO(MetodoPago metodoPago) {

        MetodoPagoDTO dto = new MetodoPagoDTO();

        dto.setId(metodoPago.getId());
        dto.setNombre(metodoPago.getNombre());

        return dto;
    }
}
