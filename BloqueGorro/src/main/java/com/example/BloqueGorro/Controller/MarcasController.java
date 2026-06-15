package com.example.BloqueGorro.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.BloqueGorro.DTO.MarcasDTO;
import com.example.BloqueGorro.Model.Marcas;
import com.example.BloqueGorro.Service.MarcasService;

import jakarta.validation.Valid;




@RestController
@RequestMapping("/api/v1/Marcas")
public class MarcasController {

    @Autowired
    MarcasService marcasService;

     //Mostrar todas las Marcas
    @GetMapping
    public ResponseEntity<?> MarcasTodas(){
        List<MarcasDTO> marcas = marcasService.MostrarTodas();
        if (!marcas.isEmpty()) {
            return new ResponseEntity<>("No hay Marcas", HttpStatus.OK);
        }
        return new ResponseEntity<>("No hay marcas", HttpStatus.NO_CONTENT);
    }

    //Buscar por id
    @GetMapping("/{id}")
    public ResponseEntity<?> BuscarPorId(@PathVariable Integer id){
        try {
            MarcasDTO marcas = marcasService.buscarPorId(id);
            return new ResponseEntity<>(marcas, HttpStatus.ACCEPTED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("No se encontro la Marca", HttpStatus.NOT_FOUND);
        }
    }

    //Agregar
    @PostMapping
    public ResponseEntity<?> agregarMarcas(@Valid @RequestBody Marcas marcas){
        try {
            return new ResponseEntity<>(marcasService.guardarMarcas(marcas), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("No se guardo la Marca", HttpStatus.BAD_REQUEST);
        }
    }

    //Actualizar
    
    

    //Eliminar
    

}

