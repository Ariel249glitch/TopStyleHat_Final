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

import topstylehut.bloquecliente.DTO.RegionDTO;
import topstylehut.bloquecliente.Model.Region;
import topstylehut.bloquecliente.Service.Regionservice;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/regiones")
@Tag(name = "Region", description = "Módulo de gestión de regiones")
@SuppressWarnings("all")
public class RegionController {

    @Autowired
    private Regionservice regionService;

    // --- MOSTRAR TODOS LOS REGISTROS ---
    @Operation(summary = "Listar todas las regiones")
    @GetMapping
    public ResponseEntity<List<RegionDTO>> listarTodas() {
        
        List<RegionDTO> lista = this.regionService.MostrarTodas();
        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Estado 204
        }
        return new ResponseEntity<>(lista, HttpStatus.OK); // Estado 200
    }

    // --- BUSCAR REGISTRO POR ID ---
    @Operation(summary = "Buscar región por ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        try {
            
            RegionDTO regionDTO = this.regionService.buscarPorId(id);
            return new ResponseEntity<>(regionDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Región no encontrada", HttpStatus.NOT_FOUND); // Estado 404
        }
    }

    // --- GUARDAR UN NUEVO REGISTRO ---
    @Operation(summary = "Registrar nueva región")
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Region nuevaRegion) {
        try {
            
            RegionDTO regionGuardada = this.regionService.guardarRegion(nuevaRegion);
            return new ResponseEntity<>(regionGuardada, HttpStatus.CREATED); // Estado 201
        } catch (Exception e) {
            return new ResponseEntity<>("Error al guardar", HttpStatus.BAD_REQUEST); // Estado 400
        }
    }

    // --- ACTUALIZAR REGISTRO EXISTENTE ---
    @Operation(summary = "Actualizar región por ID")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody Region region) {
        try {
            
            Region actualizado = this.regionService.actualizarRegion(id, region);
            
            
            RegionDTO resultadoDTO = new RegionDTO();
            resultadoDTO.setId(actualizado.getId());
            resultadoDTO.setNombre(actualizado.getNombre());
            
            return new ResponseEntity<>(resultadoDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // --- ELIMINAR REGISTRO POR ID ---
    @Operation(summary = "Eliminar región por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            
            String resultado = this.regionService.EliminarRegion(id);
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } catch (RuntimeException e) {
            
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}