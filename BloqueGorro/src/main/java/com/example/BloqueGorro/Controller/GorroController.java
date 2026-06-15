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

import com.example.BloqueGorro.DTO.GorroDTO;
import com.example.BloqueGorro.Model.Gorro;
import com.example.BloqueGorro.Service.GorroService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/gorros")
@Tag(name = "Gorro", description = "Módulo de gestión del catálogo de gorros")
@SuppressWarnings("all")
public class GorroController {

    @Autowired
    private GorroService gorroService;

    // MOSTRAR TODOS LOS REGISTROS
    @Operation(summary = "Listar todos los gorros")
    @GetMapping
    public ResponseEntity<List<GorroDTO>> listarTodas() {
        
        List<GorroDTO> lista = this.gorroService.obtenerTodos();
        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Estado 204
        }
        return new ResponseEntity<>(lista, HttpStatus.OK); // Estado 200
    }

    // --- BUSCAR REGISTRO POR ID ---
    @Operation(summary = "Buscar gorro por ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        try {
            
            GorroDTO gorroDTO = this.gorroService.buscarPorId(id);
            return new ResponseEntity<>(gorroDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Gorro no encontrado", HttpStatus.NOT_FOUND); // Estado 404
        }
    }

    // --- GUARDAR UN NUEVO REGISTRO ---
    @Operation(summary = "Registrar nuevo gorro")
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Gorro nuevoGorro) {
        try {
            
            GorroDTO gorroGuardado = this.gorroService.guardarGorro(nuevoGorro);
            return new ResponseEntity<>(gorroGuardado, HttpStatus.CREATED); // Estado 201
        } catch (Exception e) {
            return new ResponseEntity<>("Error al guardar", HttpStatus.BAD_REQUEST); // Estado 400
        }
    }

    // --- ACTUALIZAR REGISTRO EXISTENTE ---
    @Operation(summary = "Actualizar gorro por ID")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody Gorro gorro) {
        try {
            Gorro actualizado = this.gorroService.ActualizarGorro(id, gorro);
            
            
            GorroDTO resultadoDTO = new GorroDTO();
            resultadoDTO.setId(actualizado.getId());
            resultadoDTO.setNombre(actualizado.getNombre());
            resultadoDTO.setTalla(actualizado.getTalla());
            resultadoDTO.setPrecio(actualizado.getPrecio());
            
            return new ResponseEntity<>(resultadoDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    //ELIMINAR REGISTRO POR ID 
    @Operation(summary = "Eliminar gorro por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        
        String resultado = this.gorroService.Eliminar(id);
        if (resultado.contains("Imposible de eliminar") || resultado.contains("No existe")) {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(resultado, HttpStatus.OK);
    }
}
