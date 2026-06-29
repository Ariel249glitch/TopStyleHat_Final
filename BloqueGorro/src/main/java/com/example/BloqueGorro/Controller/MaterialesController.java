package com.example.BloqueGorro.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.BloqueGorro.DTO.MaterialesDTO;
import com.example.BloqueGorro.Model.Materiales;
import com.example.BloqueGorro.Service.MaterialesService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/v1/materiales")
public class MaterialesController {

    @Autowired
    MaterialesService materialesService;

     //Mostrar todos los materiales
    @Operation(summary = "Listar todos los Materiales")
    @GetMapping
    public ResponseEntity<?> MostrarTodosLosMateriales(){
        List <MaterialesDTO> materiales = materialesService.obtenerTodos();
        if (!materiales.isEmpty()) {
            return new ResponseEntity<>("No hay Materiales", HttpStatus.OK);
        }
        return new ResponseEntity<>("No hay Materiales", HttpStatus.NO_CONTENT);
    }

     //Buscar por id
    @Operation(summary = "Buscar materiales por id")
    @GetMapping("/{id}")
    public ResponseEntity<?> BuscarPorId(@PathVariable Integer id){
        try {
            MaterialesDTO materiales = materialesService.buscarPorId(id);
            return new ResponseEntity<>(materiales, HttpStatus.ACCEPTED); 
        } catch (RuntimeException e) {
            return new ResponseEntity<>("No se encontraron materiales", HttpStatus.NOT_FOUND);
        }
    }

    //Agregar
    @Operation(summary = "Agregar materiales")
    @PostMapping
    public ResponseEntity<?> agregarMaterial(@Valid @RequestBody Materiales materiales){
        try {
            return new ResponseEntity<>(materialesService.guardarMateriales(materiales), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("No se guardaron materiales", HttpStatus.BAD_REQUEST);
        }
    }

    //Eliminar
    @Operation(summary = "Eliminar materiales")
    @DeleteMapping
    public ResponseEntity<String> eliminarStuff(@PathVariable Integer id){
        String resultado = materialesService.eliminarMateriales(id);

        if (resultado.contains("Eliminados")){
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
    }




}

