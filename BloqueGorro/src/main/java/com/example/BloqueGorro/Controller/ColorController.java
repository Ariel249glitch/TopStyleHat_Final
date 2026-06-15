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

import com.example.BloqueGorro.DTO.ColorDTO;
import com.example.BloqueGorro.Model.Color;
import com.example.BloqueGorro.Service.ColorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;



@RestController
@RequestMapping("/api/v1/colores")
@Tag(name = "Color", description = "Módulo de gestión de colores")
@SuppressWarnings("all")
public class ColorController {

    @Autowired
    private ColorService colorService;

    // MOSTRAR TODOS LOS REGISTROS
    @Operation(summary = "Listar todos los colores")
    @GetMapping
    public ResponseEntity<List<ColorDTO>> listarTodas() {
        
        List<ColorDTO> lista = this.colorService.obtenerTodos();
        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    //BUSCAR REGISTRO POR ID
    @Operation(summary = "Buscar color por ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        try {
            
            ColorDTO colorDTO = this.colorService.buscarPorId(id);
            return new ResponseEntity<>(colorDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Color no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    //GUARDAR UN NUEVO REGISTRO
    @Operation(summary = "Registrar nuevo color")
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Color nuevoColor) {
        try {
            
            Color guardado = this.colorService.guardar(nuevoColor);
            
            
            ColorDTO resultadoDTO = new ColorDTO();
            resultadoDTO.setId(guardado.getId());
            resultadoDTO.setNombre(guardado.getNombre());
            
            return new ResponseEntity<>(resultadoDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al guardar", HttpStatus.BAD_REQUEST);
        }
    }

    //ACTUALIZAR REGISTRO EXISTENTE
    @Operation(summary = "Actualizar color por ID")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody Color color) {
        try {
            Color actualizado = this.colorService.actualizar(id, color);
            
            
            ColorDTO resultadoDTO = new ColorDTO();
            resultadoDTO.setId(actualizado.getId());
            resultadoDTO.setNombre(actualizado.getNombre());
            
            return new ResponseEntity<>(resultadoDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    //ELIMINAR REGISTRO POR ID
    @Operation(summary = "Eliminar color por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        
        String resultado = this.colorService.eliminar(id);
        if (resultado.contains("Color no existe")) {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(resultado, HttpStatus.OK);
    }
}
