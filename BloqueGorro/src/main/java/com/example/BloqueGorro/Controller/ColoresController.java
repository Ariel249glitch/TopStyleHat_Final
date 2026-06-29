package com.example.BloqueGorro.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.BloqueGorro.DTO.ColoresDTO;
import com.example.BloqueGorro.Service.ColoresService;

import io.swagger.v3.oas.annotations.Operation;


@RestController
@RequestMapping("/api/v1/colores")
public class ColoresController {

    @Autowired
    private ColoresService coloresService;

    @Operation(summary = "Listar todos los colores")
    @GetMapping
    public ResponseEntity<?> listarTodas() {
        List<ColoresDTO> relaciones = coloresService.obtenerTodas();
        if (relaciones.isEmpty()) {
            return new ResponseEntity<>("No hay relaciones Gorro-Color", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(relaciones, HttpStatus.OK);
    }

    @Operation(summary = "Buscar color por ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        try {
            ColoresDTO relacion = coloresService.buscarPorId(id);
            return new ResponseEntity<>(relacion, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Relación no encontrada", HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Buscar Gorro por su ID")
    @GetMapping("/gorro/{gorroId}")
    public ResponseEntity<?> buscarPorGorro(@PathVariable Integer gorroId) {
        List<ColoresDTO> relaciones = coloresService.buscarPorGorro(gorroId);
        if (relaciones.isEmpty()) {
            return new ResponseEntity<>("Este gorro no tiene colores asignados", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(relaciones, HttpStatus.OK);
    }

    @Operation(summary = "Buscar por color")
    @GetMapping("/color/{colorId}")
    public ResponseEntity<?> buscarPorColor(@PathVariable Integer colorId) {
        List<ColoresDTO> relaciones = coloresService.buscarPorColor(colorId);
        if (relaciones.isEmpty()) {
            return new ResponseEntity<>("Este color no está asignado a ningún gorro", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(relaciones, HttpStatus.OK);
    }

    @Operation(summary = "Asignar color a gorro")
    @PostMapping("/gorro/{gorroId}/color/{colorId}")
    public ResponseEntity<?> asignarColorAGorro(@PathVariable Integer gorroId, @PathVariable Integer colorId) {
        try {
            ColoresDTO nuevaRelacion = coloresService.asignarColorAGorro(gorroId, colorId);
            return new ResponseEntity<>(nuevaRelacion, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Eliminar relacion")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarRelacion(@PathVariable Integer id) {
        String resultado = coloresService.eliminarRelacion(id);
        if (resultado.contains("exitosamente")) {
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
    }
}

