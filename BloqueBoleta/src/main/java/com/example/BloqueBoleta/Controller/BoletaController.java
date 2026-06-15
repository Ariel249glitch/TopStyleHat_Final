package com.example.BloqueBoleta.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.BloqueBoleta.DTO.BoletaDTO;
import com.example.BloqueBoleta.Model.Boleta;
import com.example.BloqueBoleta.Service.BoletaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/boletas")
@Tag(name = "Boleta", description = "Módulo de gestión de boletas")
public class BoletaController {

    @Autowired
    private BoletaService boletaService;

    // Listar todas
    @Operation(summary = "Listar todas las boletas")
    @GetMapping
    public ResponseEntity<List<BoletaDTO>> listarTodas() {

        List<BoletaDTO> lista = boletaService.obtenerTodas();

        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    // Buscar por ID
    @Operation(summary = "Buscar boleta por ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {

        try {

            BoletaDTO boletaDTO = boletaService.buscarPorId(id);

            return new ResponseEntity<>(boletaDTO, HttpStatus.OK);

        } catch (RuntimeException e) {

            return new ResponseEntity<>(
                    e.getMessage(),
                    HttpStatus.NOT_FOUND);
        }
    }

    // Crear
    @Operation(summary = "Registrar nueva boleta")
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Boleta nuevaBoleta) {

        try {

            Boleta guardada = boletaService.guardar(nuevaBoleta);

            BoletaDTO dto = boletaService.convertirADTO(guardada);

            return new ResponseEntity<>(dto, HttpStatus.CREATED);

        } catch (Exception e) {

            return new ResponseEntity<>(
                    "Error al guardar boleta",
                    HttpStatus.BAD_REQUEST);
        }
    }

    // Actualizar
    @Operation(summary = "Actualizar boleta")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(
            @PathVariable Integer id,
            @RequestBody Boleta boleta) {

        try {

            Boleta actualizada =
                    boletaService.actualizar(id, boleta);

            BoletaDTO dto =
                    boletaService.convertirADTO(actualizada);

            return new ResponseEntity<>(dto, HttpStatus.OK);

        } catch (RuntimeException e) {

            return new ResponseEntity<>(
                    e.getMessage(),
                    HttpStatus.NOT_FOUND);
        }
    }

    // Eliminar
    @Operation(summary = "Eliminar boleta")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {

        try {

            String resultado = boletaService.eliminar(id);

            return new ResponseEntity<>(
                    resultado,
                    HttpStatus.OK);

        } catch (RuntimeException e) {

            return new ResponseEntity<>(
                    e.getMessage(),
                    HttpStatus.NOT_FOUND);
        }
    }
}
