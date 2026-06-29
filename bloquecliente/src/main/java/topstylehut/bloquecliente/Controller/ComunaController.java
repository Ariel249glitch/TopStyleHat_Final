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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import topstylehut.bloquecliente.DTO.ComunaDTO;
import topstylehut.bloquecliente.Model.Comuna;
import topstylehut.bloquecliente.Service.ComunaService;

@RestController
@RequestMapping("/api/v1/comunas")
@Tag(name = "Comuna", description = "Módulo de gestión de comunas")
@SuppressWarnings("all")
public class ComunaController {

    @Autowired
    private ComunaService comunaService;

    //  MOSTRAR TODOS LOS REGISTROS 
    @Operation(summary = "Listar todas las comunas", description = "Muestra todos las comunas")
    @GetMapping
    public ResponseEntity<List<ComunaDTO>> listarTodas() {
        List<ComunaDTO> lista = this.comunaService.obtenerTodas(); // Adaptado a obtenerTodas()
        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    // --- BUSCAR REGISTRO POR ID ---
    @Operation(summary = "Buscar comuna por ID", description = "Buscar una comuna por ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        try {
            ComunaDTO comunaDTO = this.comunaService.buscarPorId(id);
            return new ResponseEntity<>(comunaDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Comuna no encontrada", HttpStatus.NOT_FOUND);
        }
    }

    // --- GUARDAR UN NUEVO REGISTRO ---
    @Operation(summary = "Registrar nueva comuna", description = "Registra una nueva comuna en el Sistema")
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Comuna nuevaComuna) {
        try {
            ComunaDTO comunaGuardada = this.comunaService.guardar(nuevaComuna); // Adaptado a guardar()
            return new ResponseEntity<>(comunaGuardada, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al guardar", HttpStatus.BAD_REQUEST);
        }
    }

    // --- ACTUALIZAR REGISTRO EXISTENTE ---
    @Operation(summary = "Actualizar comuna por ID", description = "Actualiza una comuna por su id")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody Comuna comuna) {
        try {
            comuna.setId(id);
            ComunaDTO resultadoDTO = this.comunaService.guardar(comuna); // Sincronizado mediante guardar()
            return new ResponseEntity<>(resultadoDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // --- ELIMINAR REGISTRO POR ID ---
    @Operation(summary = "Eliminar comuna por ID", description = "Elimina una comuna del sistema")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            String resultado = this.comunaService.eliminar(id); // Adaptado a eliminar()
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}