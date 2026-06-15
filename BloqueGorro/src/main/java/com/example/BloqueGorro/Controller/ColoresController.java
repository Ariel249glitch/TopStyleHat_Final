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


@RestController
@RequestMapping("/api/v1/colores-relaciones")
public class ColoresController {

    @Autowired
    private ColoresService coloresService;

    @GetMapping
    public ResponseEntity<?> listarTodas() {
        List<ColoresDTO> relaciones = coloresService.obtenerTodas();
        if (relaciones.isEmpty()) {
            return new ResponseEntity<>("No hay relaciones Gorro-Color", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(relaciones, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        try {
            ColoresDTO relacion = coloresService.buscarPorId(id);
            return new ResponseEntity<>(relacion, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Relación no encontrada", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/gorro/{gorroId}")
    public ResponseEntity<?> buscarPorGorro(@PathVariable Integer gorroId) {
        List<ColoresDTO> relaciones = coloresService.buscarPorGorro(gorroId);
        if (relaciones.isEmpty()) {
            return new ResponseEntity<>("Este gorro no tiene colores asignados", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(relaciones, HttpStatus.OK);
    }

    @GetMapping("/color/{colorId}")
    public ResponseEntity<?> buscarPorColor(@PathVariable Integer colorId) {
        List<ColoresDTO> relaciones = coloresService.buscarPorColor(colorId);
        if (relaciones.isEmpty()) {
            return new ResponseEntity<>("Este color no está asignado a ningún gorro", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(relaciones, HttpStatus.OK);
    }

    @PostMapping("/gorro/{gorroId}/color/{colorId}")
    public ResponseEntity<?> asignarColorAGorro(@PathVariable Integer gorroId, @PathVariable Integer colorId) {
        try {
            ColoresDTO nuevaRelacion = coloresService.asignarColorAGorro(gorroId, colorId);
            return new ResponseEntity<>(nuevaRelacion, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

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

