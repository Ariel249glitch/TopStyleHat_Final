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
@RequestMapping("/api/v1/color")
@Tag(name = "Color", description = "Módulo de gestión de colores")
@SuppressWarnings("all")
public class ColorController {

    @Autowired
    private ColorService colorService;

    //mostrar todos
    @Operation(summary = "Listar todos los colores", description = "Muestra todos los colores")
    @GetMapping
    public ResponseEntity<List<ColorDTO>> listarTodas() {
        
        List<ColorDTO> lista = this.colorService.obtenerTodos();
        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    //buscar por id
    @Operation(summary = "Buscar color por ID", description = "Busca color por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        try {
            ColorDTO colorDTO = this.colorService.buscarPorId(id);
            return new ResponseEntity<>(colorDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Color no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    //guardar
    @Operation(summary = "Registrar nuevo color", description = "Registra un nuevo color")
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Color nuevoColor) {

        try {
            return new ResponseEntity<>(colorService.guardar(nuevoColor), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    "Error al guardar color",
                    HttpStatus.BAD_REQUEST);
        }
    }

    ///actualizar
    @Operation(summary = "Actualizar color por ID", description = "Actualiza un color por su id")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody Color color) {
        try {

            Color actualizado = colorService.actualizar(id, color);

            return new ResponseEntity<>(
                    colorService.convertirADTO(actualizado),
                    HttpStatus.OK);

        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    //eliminar
    @Operation(summary = "Eliminar color por ID", description = "Elimina un color por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        
        String resultado = this.colorService.eliminar(id);
        if (resultado.contains("Color no existe")) {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(resultado, HttpStatus.OK);
    }
}
