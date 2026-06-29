package com.example.BloqueGorro.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "gorro_material") 

public class Materiales {

    @Id
    @GeneratedValue
    private Integer id;

    //Materiales - Gorro
    @ManyToOne
    @JoinColumn(name = "gorro_id")
    private Gorro gorro;

    //Materiales - Material
    @ManyToOne
    @JoinColumn(name = "material_id")
    private Material material;

}

