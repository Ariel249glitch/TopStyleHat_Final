package com.example.BloqueGorro.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.BloqueGorro.DTO.EstiloDTO;
import com.example.BloqueGorro.Model.Estilo;
import com.example.BloqueGorro.Service.EstiloService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/estilos")
@Tag(name = "Estilo", description = "Módulo de gestión de estilos")
@SuppressWarnings("all")
public class EstiloController {

    @Autowired
    private EstiloService estiloService;

    // --- MOSTRAR TODOS LOS REGISTROS ---
    @Operation(summary = "Listar todos los estilos")
    @GetMapping
    public ResponseEntity<List<EstiloDTO>> listarTodas() {
        // Llama a tu método: MostrarTodas()
        List<EstiloDTO> lista = this.estiloService.MostrarTodas();
        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Estado 204
        }
        return new ResponseEntity<>(lista, HttpStatus.OK); // Estado 200
    }

    // --- BUSCAR REGISTRO POR ID ---
    @Operation(summary = "Buscar estilo por ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        try {
            // Llama a tu método: buscarPorId()
            EstiloDTO estiloDTO = this.estiloService.buscarPorId(id);
            return new ResponseEntity<>(estiloDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Estilo no encontrado", HttpStatus.NOT_FOUND); // Estado 404
        }
    }

    // --- GUARDAR UN NUEVO REGISTRO ---
    @Operation(summary = "Registrar nuevo estilo")
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Estilo nuevoEstilo) {
        try {
            // Llama a tu método: guardarEstilo()
            EstiloDTO estiloGuardado = this.estiloService.guardarEstilo(nuevoEstilo);
            return new ResponseEntity<>(estiloGuardado, HttpStatus.CREATED); // Estado 201
        } catch (Exception e) {
            return new ResponseEntity<>("Error al guardar", HttpStatus.BAD_REQUEST); // Estado 400
        }
    }

    // --- ACTUALIZAR REGISTRO EXISTENTE ---
    @Operation(summary = "Actualizar estilo por ID")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody Estilo estilo) {
        try {
            // Llama a tu método exacto: actualizarEsti()
            Estilo actualizado = this.estiloService.actualizarEsti(id, estilo);
            
            // Armamos el DTO de forma manual ya que tu convertidor es privado en el servicio
            EstiloDTO resultadoDTO = new EstiloDTO();
            resultadoDTO.setId(actualizado.getId());
            resultadoDTO.setNombre(actualizado.getNombre());
            
            return new ResponseEntity<>(resultadoDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // --- ELIMINAR REGISTRO POR ID ---
    @Operation(summary = "Eliminar estilo por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        // Llama a tu método: EliminarEstilo()
        String resultado = this.estiloService.EliminarEstilo(id);
        if (resultado.contains("No se puede eliminar") || resultado.contains("No existe")) {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(resultado, HttpStatus.OK);
    }
}
