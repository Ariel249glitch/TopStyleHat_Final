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
import topstylehut.bloquecliente.DTO.RegionDTO; // Mantiene tu nombre exacto de clase de servicio
import topstylehut.bloquecliente.Model.Region;
import topstylehut.bloquecliente.Service.Regionservice;

@RestController
@RequestMapping("/api/v1/regiones")
@Tag(name = "Region", description = "Módulo de gestión de regiones")
@SuppressWarnings("all")
public class RegionController {

    @Autowired
    private Regionservice regionService;

    // --- MOSTRAR TODOS LOS REGISTROS ---
    @Operation(summary = "Listar todas las regiones", description = "Muestra todas las regiones")
    @GetMapping
    public ResponseEntity<List<RegionDTO>> listarTodas() {
        List<RegionDTO> lista = this.regionService.obtenerTodas(); // Adaptado a obtenerTodas()
        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    // --- BUSCAR REGISTRO POR ID ---
    @Operation(summary = "Buscar región por ID", description = "Busca una region por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        try {
            RegionDTO regionDTO = this.regionService.buscarPorId(id);
            return new ResponseEntity<>(regionDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Región no encontrada", HttpStatus.NOT_FOUND);
        }
    }

    // --- GUARDAR UN NUEVO REGISTRO ---
    @Operation(summary = "Registrar nueva región",description = "Registra una nueva region en el sistema")
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Region nuevaRegion) {
        try {
            RegionDTO regionGuardada = this.regionService.guardar(nuevaRegion); // Adaptado a guardar()
            return new ResponseEntity<>(regionGuardada, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al guardar", HttpStatus.BAD_REQUEST);
        }
    }

    // --- ACTUALIZAR REGISTRO EXISTENTE ---
    @Operation(summary = "Actualizar región por ID", description = "Actualiza una region por su ID")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody Region region) {
        try {
            region.setId(id);
            RegionDTO resultadoDTO = this.regionService.guardar(region); // Sincronizado mediante guardar()
            return new ResponseEntity<>(resultadoDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // --- ELIMINAR REGISTRO POR ID ---
    @Operation(summary = "Eliminar región por ID", description = "Elimina una region del sistema")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            String resultado = this.regionService.eliminar(id); // Adaptado a eliminar()
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}