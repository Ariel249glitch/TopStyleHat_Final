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
@Entity(name = "Gorro_Marca")

public class Marcas {

    @Id
    @GeneratedValue
    private Integer id;

    //Marcas - gorro
    @ManyToOne
    @JoinColumn(name = "gorro_id")
    private Gorro gorro;

    //Marcas - marca
    @ManyToOne
    @JoinColumn(name = "marca_id")
    private Marca marca;

}

