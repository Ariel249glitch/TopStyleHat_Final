package com.example.BloqueBoleta.Service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.BloqueBoleta.DTO.MetodoPagoDTO;
import com.example.BloqueBoleta.Model.MetodoPago;
import com.example.BloqueBoleta.Repository.MetodoPagoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class MetodoPagoService {

    private static final Logger log = LoggerFactory.getLogger(MetodoPagoService.class);

    @Autowired
    private MetodoPagoRepository metodoPagoRepository;

    // Mostrar todos
    public List<MetodoPagoDTO> obtenerTodos() {
        log.info("Obteniendo todos los métodos de pago");
        List<MetodoPagoDTO> lista = new ArrayList<>();
        for (MetodoPago metodoPago : metodoPagoRepository.findAll()) {
            lista.add(convertirADTO(metodoPago));
        }
        log.info("Total de métodos de pago: {}", lista.size());
        return lista;
    }

    // Buscar por ID
    public MetodoPagoDTO buscarPorId(Integer id) {
        log.info("Buscando método de pago con ID: {}", id);
        try {
            MetodoPago metodoPago = metodoPagoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Método de pago no encontrado"));
            return convertirADTO(metodoPago);
        } catch (RuntimeException e) {
            log.error("Error al buscar método de pago ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    // Guardar
    public MetodoPagoDTO guardar(MetodoPago metodoPago) {
        log.info("Guardando nuevo método de pago: {}", metodoPago);
        MetodoPago nuevoPago = metodoPagoRepository.save(metodoPago);
        log.info("Método de pago guardado con ID: {}", nuevoPago.getId());
        return convertirADTO(nuevoPago);
    }

    // Actualizar
    public MetodoPago actualizar(Integer id, MetodoPago metodoPagoActualizado) {
        log.info("Actualizando método de pago con ID: {}", id);
        try {
            MetodoPago metodoPagoExistente = metodoPagoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Método de pago no existe"));

            if (metodoPagoActualizado.getNombre() != null) {
                metodoPagoExistente.setNombre(metodoPagoActualizado.getNombre());
            }

            MetodoPago updated = metodoPagoRepository.save(metodoPagoExistente);
            log.info("Método de pago con ID {} actualizado correctamente", id);
            return updated;
        } catch (RuntimeException e) {
            log.error("Error al actualizar método de pago ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    // Eliminar
    public String eliminar(Integer id) {
        log.info("Eliminando método de pago con ID: {}", id);
        try {
            MetodoPago metodoPago = metodoPagoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Método de pago no existe"));
            metodoPagoRepository.delete(metodoPago);
            String mensaje = "El método de pago '" + metodoPago.getNombre() + "' ha sido eliminado exitosamente";
            log.info(mensaje);
            return mensaje;
        } catch (RuntimeException e) {
            log.error("Error al eliminar método de pago ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    // Convertir Entity a DTO
    public MetodoPagoDTO convertirADTO(MetodoPago metodoPago) {
        MetodoPagoDTO dto = new MetodoPagoDTO();
        dto.setId(metodoPago.getId());
        dto.setNombre(metodoPago.getNombre());
        return dto;
    }
}