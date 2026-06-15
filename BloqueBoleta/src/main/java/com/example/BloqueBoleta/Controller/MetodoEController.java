package com.example.BloqueBoleta.Controller;

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

import com.example.BloqueBoleta.DTO.MetodoEDTO;
import com.example.BloqueBoleta.Model.MetodoE;
import com.example.BloqueBoleta.Service.MetodoEService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/metodos-entrega")
@Tag(name = "MetodoE", description = "Módulo de gestión de métodos de entrega")
@SuppressWarnings("all")
public class MetodoEController {
    @Autowired
    private MetodoEService metodoEService;

    // --- MOSTRAR TODOS LOS REGISTROS ---
    @Operation(summary = "Listar todos los métodos de entrega")
    @GetMapping
    public ResponseEntity<List<MetodoEDTO>> listarTodas() {
        // Llama a tu método: MostrarTodas()
        List<MetodoEDTO> lista = this.metodoEService.MostrarTodas();
        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Estado 204
        }
        return new ResponseEntity<>(lista, HttpStatus.OK); // Estado 200
    }

    // --- BUSCAR REGISTRO POR ID ---
    @Operation(summary = "Buscar método de entrega por ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        try {
            // Llama a tu método: buscarPorId()
            MetodoEDTO metodoEDTO = this.metodoEService.buscarPorId(id);
            return new ResponseEntity<>(metodoEDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Método de entrega no encontrado", HttpStatus.NOT_FOUND); // Estado 404
        }
    }

    // --- GUARDAR UN NUEVO REGISTRO ---
    @Operation(summary = "Registrar nuevo método de entrega")
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody MetodoE nuevoMetodoE) {
        try {
            // Llama a tu método: guardarMetodoE()
            MetodoEDTO guardado = this.metodoEService.guardarMetodoE(nuevoMetodoE);
            return new ResponseEntity<>(guardado, HttpStatus.CREATED); // Estado 201
        } catch (Exception e) {
            return new ResponseEntity<>("Error al guardar", HttpStatus.BAD_REQUEST); // Estado 400
        }
    }

    // --- ACTUALIZAR REGISTRO EXISTENTE ---
    @Operation(summary = "Actualizar método de entrega por ID")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody MetodoE metodoE) {
        try {
            // Llama a tu método: actualizarMetodoE()
            MetodoE actualizado = this.metodoEService.actualizarMetodoE(id, metodoE);
            
            // Armamos el DTO manual mapeando también el campo 'tiempo'
            MetodoEDTO resultadoDTO = new MetodoEDTO();
            resultadoDTO.setId(actualizado.getId());
            resultadoDTO.setNombre(actualizado.getNombre());
            resultadoDTO.setTiempo(actualizado.getTiempo());
            
            return new ResponseEntity<>(resultadoDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // --- ELIMINAR REGISTRO POR ID ---
    @Operation(summary = "Eliminar método de entrega por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            // Llama a tu método: EliminarMetodoE()
            String resultado = this.metodoEService.EliminarMetodoE(id);
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
