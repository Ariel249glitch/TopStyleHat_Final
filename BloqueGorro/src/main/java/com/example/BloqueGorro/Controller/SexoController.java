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

import com.example.BloqueGorro.DTO.SexoDTO;
import com.example.BloqueGorro.Model.Sexo;
import com.example.BloqueGorro.Service.SexoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/v1/sexos")
@Tag(name = "Sexo", description = "Módulo de gestión de sexos")
@SuppressWarnings("all")
public class SexoController {

    @Autowired
    private SexoService sexoService;

    // --- MOSTRAR TODOS LOS REGISTROS ---
    @Operation(summary = "Listar todos los sexos")
    @GetMapping
    public ResponseEntity<List<SexoDTO>> listarTodas() {
        List<SexoDTO> lista = this.sexoService.mostrarTodos();
        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Estado 204: Lista vacía
        }
        return new ResponseEntity<>(lista, HttpStatus.OK); // Estado 200: Devuelve los DTOs
    }

    // --- BUSCAR REGISTRO POR ID ---
    @Operation(summary = "Buscar sexo por ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        try {
            SexoDTO sexoDTO = this.sexoService.buscarPorId(id);
            return new ResponseEntity<>(sexoDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Sexo no encontrado", HttpStatus.NOT_FOUND); // Estado 404
        }
    }

    // --- GUARDAR UN NUEVO REGISTRO ---
    @Operation(summary = "Registrar nuevo sexo")
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Sexo nuevoSexo) { // Recibe la entidad original en JSON
        try {
            SexoDTO sexoGuardado = this.sexoService.guardarSexo(nuevoSexo);
            return new ResponseEntity<>(sexoGuardado, HttpStatus.CREATED); // Estado 201: Creado
        } catch (Exception e) {
            return new ResponseEntity<>("Error al guardar", HttpStatus.BAD_REQUEST); // Estado 400
        }
    }

    // --- ACTUALIZAR REGISTRO EXISTENTE ---
    @Operation(summary = "Actualizar sexo por ID")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody Sexo sexo) {
        try {
            Sexo actualizado = this.sexoService.actualizarSexo(id, sexo);
            SexoDTO resultadoDTO = this.sexoService.convertirADTO(actualizado); // Convierte el resultado a DTO
            return new ResponseEntity<>(resultadoDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // --- ELIMINAR REGISTRO POR ID ---
    @Operation(summary = "Eliminar sexo por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        String resultado = this.sexoService.eliminarSexo(id);
        if (resultado.contains("no se puede eliminar") || resultado.contains("No existe")) {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(resultado, HttpStatus.OK);
    }
}
