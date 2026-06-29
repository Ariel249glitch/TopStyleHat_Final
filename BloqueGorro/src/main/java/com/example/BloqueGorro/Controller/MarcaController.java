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

import com.example.BloqueGorro.DTO.MarcaDTO;
import com.example.BloqueGorro.Model.Marca;
import com.example.BloqueGorro.Service.MarcaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/marca")
@Tag(name = "Marca", description = "Módulo de gestión de marcas")

public class MarcaController {

    @Autowired
    private MarcaService marcaService;

    // --- MOSTRAR TODOS LOS REGISTROS ---
    @Operation(summary = "Listar todas las marcas", description = "Muestra todas las marcas")
    @GetMapping
    public ResponseEntity<List<MarcaDTO>> listarTodas() {
        
        List<MarcaDTO> lista = this.marcaService.MostrarTodas();
        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    // --- BUSCAR REGISTRO POR ID ---
    @Operation(summary = "Buscar marca por ID", description = "Busca una marca por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        try {
            // Llama a tu método: buscarPorId()
            MarcaDTO marcaDTO = this.marcaService.buscarPorId(id);
            return new ResponseEntity<>(marcaDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Marca no encontrada", HttpStatus.NOT_FOUND); // Estado 404
        }
    }

    // --- GUARDAR UN NUEVO REGISTRO ---
    @Operation(summary = "Registrar nueva marca", description = "Registra una nueva marca")
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Marca nuevaMarca) {
        try {
            
            MarcaDTO marcaGuardada = this.marcaService.guardarMarca(nuevaMarca);
            return new ResponseEntity<>(marcaGuardada, HttpStatus.CREATED); // Estado 201
        } catch (Exception e) {
            return new ResponseEntity<>("Error al guardar", HttpStatus.BAD_REQUEST); // Estado 400
        }
    }

    // --- ACTUALIZAR REGISTRO EXISTENTE ---
    @Operation(summary = "Actualizar marca por ID", description = "Actualiza una marca existente")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody Marca marca) {
        try {
            
            Marca actualizado = this.marcaService.actualizarMarca(id, marca);
            
            // Como tu convertirADTO en este servicio es privado, armamos el DTO manualmente aquí
            MarcaDTO resultadoDTO = new MarcaDTO();
            resultadoDTO.setId(actualizado.getId());
            resultadoDTO.setNombre(actualizado.getNombre());
            
            return new ResponseEntity<>(resultadoDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // --- ELIMINAR REGISTRO POR ID ---
    @Operation(summary = "Eliminar marca por ID", description = "Elimina una marca existente")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        
        String resultado = marcaService.EliminarMarca(id);
        if (resultado.contains("No se puede eliminar") || resultado.contains("No existe")) {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(resultado, HttpStatus.OK);
    }
}
