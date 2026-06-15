package topstylehut.bloquecliente.Controller;

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

import topstylehut.bloquecliente.DTO.ComunaDTO;
import topstylehut.bloquecliente.Model.Comuna;
import topstylehut.bloquecliente.Service.ComunaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/comunas")
@Tag(name = "Comuna", description = "Módulo de gestión de comunas")
@SuppressWarnings("all")
public class ComunaController {

    @Autowired
    private ComunaService comunaService;

    //  MOSTRAR TODOS LOS REGISTROS 
    @Operation(summary = "Listar todas las comunas")
    @GetMapping
    public ResponseEntity<List<ComunaDTO>> listarTodas() {
        
        List<ComunaDTO> lista = this.comunaService.MostrarTodas();
        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Estado 204
        }
        return new ResponseEntity<>(lista, HttpStatus.OK); // Estado 200
    }

    // --- BUSCAR REGISTRO POR ID ---
    @Operation(summary = "Buscar comuna por ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        try {
            
            ComunaDTO comunaDTO = this.comunaService.buscarPorId(id);
            return new ResponseEntity<>(comunaDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Comuna no encontrada", HttpStatus.NOT_FOUND); // Estado 404
        }
    }

    // --- GUARDAR UN NUEVO REGISTRO ---
    @Operation(summary = "Registrar nueva comuna")
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Comuna nuevaComuna) {
        try {
            
            ComunaDTO comunaGuardada = this.comunaService.guardarComuna(nuevaComuna);
            return new ResponseEntity<>(comunaGuardada, HttpStatus.CREATED); // Estado 201
        } catch (Exception e) {
            return new ResponseEntity<>("Error al guardar", HttpStatus.BAD_REQUEST); // Estado 400
        }
    }

    // --- ACTUALIZAR REGISTRO EXISTENTE ---
    @Operation(summary = "Actualizar comuna por ID")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody Comuna comuna) {
        try {
            
            Comuna actualizado = this.comunaService.actualizarComu(id, comuna);
            
            
            ComunaDTO resultadoDTO = new ComunaDTO();
            resultadoDTO.setId(actualizado.getId());
            resultadoDTO.setNombre(actualizado.getNombre());
            
            return new ResponseEntity<>(resultadoDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // --- ELIMINAR REGISTRO POR ID ---
    @Operation(summary = "Eliminar comuna por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            
            String resultado = this.comunaService.EliminarComuna(id);
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}