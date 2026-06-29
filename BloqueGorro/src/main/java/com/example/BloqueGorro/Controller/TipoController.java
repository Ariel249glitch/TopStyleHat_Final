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

import com.example.BloqueGorro.DTO.TipoDTO;
import com.example.BloqueGorro.Model.Tipo;
import com.example.BloqueGorro.Service.TipoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/tipos")
@Tag(name = "Tipo", description = "Módulo de gestión de tipos")
@SuppressWarnings("all")
public class TipoController {

    @Autowired
    private TipoService tipoService;

    //mostrar todo
    @Operation(summary = "Listar todos los tipos")
    @GetMapping
    public ResponseEntity<List<TipoDTO>> listarTodas() {
        
        List<TipoDTO> lista = this.tipoService.MostrarTodas();
        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
        }
        return new ResponseEntity<>(lista, HttpStatus.OK); 
    }

    //buscar por id
    @Operation(summary = "Buscar tipo por ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        try {
            
            TipoDTO tipoDTO = this.tipoService.buscarPorId(id);
            return new ResponseEntity<>(tipoDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Tipo no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    //Guardar
    @Operation(summary = "Registrar nuevo tipo")
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Tipo nuevoTipo) {
        try {
            TipoDTO tipoGuardado = this.tipoService.guardarTipo(nuevoTipo);
            return new ResponseEntity<>(tipoGuardado, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al guardar", HttpStatus.BAD_REQUEST);
        }
    }

    //Actualizar
    @Operation(summary = "Actualizar tipo por ID")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody Tipo tipo) {
        try {

            Tipo actualizado = tipoService.actualizarTipo(id, tipo);

            return new ResponseEntity<>(
                    tipoService.convertirADTO(actualizado),
                    HttpStatus.OK);

        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    //Eliminar
    @Operation(summary = "Eliminar tipo por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        String resultado = this.tipoService.EliminarTipo(id);
        if (resultado.contains("No se puede eliminar") || resultado.contains("No existe")) {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(resultado, HttpStatus.OK);
    }
}
