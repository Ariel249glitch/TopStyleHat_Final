package com.example.BloqueGorro.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "Gorro - Materiales") 

public class Materiales {

    @Id
    @GeneratedValue
    private Integer id;

    @NotBlank (message = "El nombre es obligatorio")
    @Size (min = 3, max = 20, message = "El nombre debe tener entre 3 y 20 caracteres")
    private String nombre;

    //Materiales - Gorro
    @ManyToOne
    @JoinColumn(name = "gorro_id")
    private Gorro gorro;

    //Materiales - Material
    @ManyToOne
    @JoinColumn(name = "material_id")
    private Material material;

}

