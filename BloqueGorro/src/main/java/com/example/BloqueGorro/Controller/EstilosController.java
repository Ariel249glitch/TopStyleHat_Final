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

import com.example.BloqueGorro.DTO.EstilosDTO;
import com.example.BloqueGorro.Model.Estilos;
import com.example.BloqueGorro.Service.EstilosService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/v1/Estilos")
public class EstilosController {

    @Autowired
    EstilosService estilosService;

    //Mostrar todos los tipos 
    @GetMapping
    public ResponseEntity<?> TodosLosEstilos(){
        List<EstilosDTO> estiloss = estilosService.MostrarTodas();
        if (!estiloss.isEmpty()) {
            return new ResponseEntity<>(estiloss, HttpStatus.OK);
        }
        return new ResponseEntity<>("No hay Estilos", HttpStatus.NO_CONTENT);
    }

    //Agregar
    @PostMapping
    public ResponseEntity<?> agregarEsti(@Valid @RequestBody Estilos estilos){
        try {
            return new ResponseEntity<>(estilosService.guardarEstilos(estilos), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("No se guardo el estilo", HttpStatus.BAD_REQUEST);
        }
    }

    //Actualizar
    @PutMapping("/id")
    public ResponseEntity<Estilos> actualizarEsti(@PathVariable Integer id, @RequestBody Estilos Esti){
        try {
            Estilos newEsti = estilosService.actualizarEstis(id, Esti);
            return new ResponseEntity<>(newEsti, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Eliminar
    @DeleteMapping
    public ResponseEntity<String> EliminarEstilos(@PathVariable Integer id){
        String resultado = estilosService.EliminarEstilos(id);

        if (resultado.contains("Eliminado")){
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }

    }
}


