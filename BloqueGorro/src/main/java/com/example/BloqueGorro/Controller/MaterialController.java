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

import com.example.BloqueGorro.DTO.MaterialDTO;
import com.example.BloqueGorro.Model.Material;
import com.example.BloqueGorro.Service.MaterialService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/materiales")
@Tag(name = "Material", description = "Módulo de gestión de materiales")
@SuppressWarnings("all")
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    // --- MOSTRAR TODOS LOS REGISTROS ---
    @Operation(summary = "Listar todos los materiales")
    @GetMapping
    public ResponseEntity<List<MaterialDTO>> listarTodas() {
        // Llama a tu método: mostrarTodos()
        List<MaterialDTO> lista = this.materialService.mostrarTodos();
        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Estado 204
        }
        return new ResponseEntity<>(lista, HttpStatus.OK); // Estado 200
    }

    // --- BUSCAR REGISTRO POR ID ---
    @Operation(summary = "Buscar material por ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        try {
            // Llama a tu método: buscarPorId()
            MaterialDTO materialDTO = this.materialService.buscarPorId(id);
            return new ResponseEntity<>(materialDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Material no encontrado", HttpStatus.NOT_FOUND); // Estado 404
        }
    }

    // --- GUARDAR UN NUEVO REGISTRO ---
    @Operation(summary = "Registrar nuevo material")
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Material nuevoMaterial) {
        try {
            // Llama a tu método: guardarMaterial()
            MaterialDTO materialGuardado = this.materialService.guardarMaterial(nuevoMaterial);
            return new ResponseEntity<>(materialGuardado, HttpStatus.CREATED); // Estado 201
        } catch (Exception e) {
            return new ResponseEntity<>("Error al guardar", HttpStatus.BAD_REQUEST); // Estado 400
        }
    }

    // --- ACTUALIZAR REGISTRO EXISTENTE ---
    @Operation(summary = "Actualizar material por ID")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody Material material) {
        try {
            // Llama a tu método: actualizarMaterial()
            Material actualizado = this.materialService.actualizarMaterial(id, material);
            
            // Armamos el DTO de forma manual ya que tu convertidor es privado en el servicio
            MaterialDTO resultadoDTO = new MaterialDTO();
            resultadoDTO.setId(actualizado.getId());
            resultadoDTO.setNombre(actualizado.getNombre());
            
            return new ResponseEntity<>(resultadoDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // --- ELIMINAR REGISTRO POR ID ---
    @Operation(summary = "Eliminar material por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        
        String resultado = this.materialService.eliminarMaterial(id);
        if (resultado.contains("no se puede eliminar") || resultado.contains("No existe")) {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(resultado, HttpStatus.OK);
    }
}
