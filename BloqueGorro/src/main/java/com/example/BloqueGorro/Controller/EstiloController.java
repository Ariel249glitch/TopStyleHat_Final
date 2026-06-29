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

import com.example.BloqueGorro.DTO.EstiloDTO;
import com.example.BloqueGorro.Model.Estilo;
import com.example.BloqueGorro.Service.EstiloService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/estilos")
@SuppressWarnings("all")
public class EstiloController {

    @Autowired
    private EstiloService estiloService;

    // --- MOSTRAR TODOS LOS REGISTROS ---
    @Operation(summary = "Listar todos los estilos", description = "Muestra todos los estilos")
    @GetMapping
    public ResponseEntity<List<EstiloDTO>> listarTodas() {
        // Llama a tu método: MostrarTodas()
        List<EstiloDTO> lista = this.estiloService.MostrarTodas();
        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Estado 204
        }
        return new ResponseEntity<>(lista, HttpStatus.OK); // Estado 200
    }

    // --- BUSCAR REGISTRO POR ID ---
    @Operation(summary = "Buscar estilo por ID", description = "busca un estilo por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        try {
            // Llama a tu método: buscarPorId()
            EstiloDTO estiloDTO = this.estiloService.buscarPorId(id);
            return new ResponseEntity<>(estiloDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Estilo no encontrado", HttpStatus.NOT_FOUND); // Estado 404
        }
    }

    @Operation(summary = "Registrar nuevo estilo", description = "Agrega un nuevo estilo al sistema")
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody EstiloDTO nuevoEstilo) {
        try {

            return new ResponseEntity<>(
                    estiloService.guardarEstilo(nuevoEstilo),
                    HttpStatus.CREATED);

        } catch (Exception e) {

            return new ResponseEntity<>(
                    "Error al guardar estilo",
                    HttpStatus.BAD_REQUEST);
        }
    }

    // actualizar
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody Estilo estilo) {
        try {

            EstiloDTO dto = new EstiloDTO();
            dto.setId(estilo.getId());
            dto.setNombre(estilo.getNombre());

            EstiloDTO actualizado = estiloService.actualizarEsti(id, dto);

            return new ResponseEntity<>(actualizado, HttpStatus.OK);

        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // --- ELIMINAR REGISTRO POR ID ---
    @Operation(summary = "Eliminar estilo por ID", description = "Eliminar estilo por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        // Llama a tu método: EliminarEstilo()
        String resultado = this.estiloService.EliminarEstilo(id);
        if (resultado.contains("No se puede eliminar") || resultado.contains("No existe")) {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(resultado, HttpStatus.OK);
    }
}
