package com.example.BloqueBoleta.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.BloqueBoleta.DTO.MetodoPagoDTO;
import com.example.BloqueBoleta.Model.MetodoPago;
import com.example.BloqueBoleta.Service.MetodoPagoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/metodos-pago")
@Tag(name = "Método de Pago", description = "Módulo de gestión de métodos de pago")
public class MetodoPagoController {

    @Autowired
    private MetodoPagoService metodoPagoService;

    // Mostrar todos
    @Operation(summary = "Listar todos los métodos de pago")
    @GetMapping
    public ResponseEntity<List<MetodoPagoDTO>> listarTodos() {

        List<MetodoPagoDTO> lista = metodoPagoService.obtenerTodos();

        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    // Buscar por ID
    @Operation(summary = "Buscar método de pago por ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {

        try {

            MetodoPagoDTO dto = metodoPagoService.buscarPorId(id);

            return new ResponseEntity<>(dto, HttpStatus.OK);

        } catch (RuntimeException e) {

            return new ResponseEntity<>(
                    e.getMessage(),
                    HttpStatus.NOT_FOUND);
        }
    }

    // Crear
    @Operation(summary = "Registrar método de pago")
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody MetodoPago metodoPago) {

        try {

            MetodoPago guardado = metodoPagoService.guardar(metodoPago);

            MetodoPagoDTO dto = metodoPagoService.convertirADTO(guardado);

            return new ResponseEntity<>(dto, HttpStatus.CREATED);

        } catch (Exception e) {

            return new ResponseEntity<>(
                    "Error al guardar método de pago",
                    HttpStatus.BAD_REQUEST);
        }
    }

    // Actualizar
    @Operation(summary = "Actualizar método de pago")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(
            @PathVariable Integer id,
            @RequestBody MetodoPago metodoPago) {

        try {

            MetodoPago actualizado =
                    metodoPagoService.actualizar(id, metodoPago);

            MetodoPagoDTO dto =
                    metodoPagoService.convertirADTO(actualizado);

            return new ResponseEntity<>(dto, HttpStatus.OK);

        } catch (RuntimeException e) {

            return new ResponseEntity<>(
                    e.getMessage(),
                    HttpStatus.NOT_FOUND);
        }
    }

    // Eliminar
    @Operation(summary = "Eliminar método de pago")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {

        try {

            String resultado = metodoPagoService.eliminar(id);

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
