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

import com.example.BloqueGorro.DTO.GorrosDTO;
import com.example.BloqueGorro.Model.Gorros;
import com.example.BloqueGorro.Service.GorrosService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/gorros")

public class GorrosController {

    @Autowired
    private GorrosService gorrosService;

    //Mostrar todos
    @GetMapping
    public ResponseEntity<?> TodosLosGorros(){
        List <GorrosDTO> gorros = gorrosService.obtenerTodos();
        if (!gorros.isEmpty()) {
            return new ResponseEntity<>(gorros, HttpStatus.OK);
        }
        return new ResponseEntity<>("No hay Gorros", HttpStatus.NO_CONTENT);
    }

    //Buscar por id
    @GetMapping("/{id}")
    public ResponseEntity<?> BuscarPorId(@PathVariable Integer id){
        try {
            GorrosDTO gorros = gorrosService.buscarPorId(id);
            return new ResponseEntity<>(gorros, HttpStatus.ACCEPTED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("No se encontro el gorro", HttpStatus.NOT_FOUND);
        }
    }

    //Guardar
    @PostMapping
    public ResponseEntity<?> agregarGorros(@Valid @RequestBody Gorros gorros){
        try {
            return new ResponseEntity<>(gorrosService.guardarGorros(gorros), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("No se guardo el gorro", HttpStatus.BAD_REQUEST);
        }
    }

    //Actualizar
    @PutMapping("/id")
    public ResponseEntity<Gorros> actualizarGorros (@PathVariable Integer id, @RequestBody Gorros hats){
        try {
            Gorros newHats = gorrosService.ActualizarGorros(id, hats);
            return new ResponseEntity<>(newHats, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Eliminar

    @DeleteMapping("/id")
    public ResponseEntity<String> eliminarGorro(@PathVariable Integer id){
        String resultado = gorrosService.Eliminar(id);

        if (resultado.contains("eliminado")) {
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
    }

}

