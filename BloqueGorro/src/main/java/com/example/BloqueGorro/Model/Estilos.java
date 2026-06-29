package com.example.BloqueGorro.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "gorro_estilo")
public class Estilos {
    @Id
    @GeneratedValue
    private Integer id;

    //Estilos - Gorro
    @ManyToOne
    @JoinColumn(name = "gorro_id")
    private Gorro gorro;

    //Estilos - estilo
    @ManyToOne
    @JoinColumn(name = "estilo_id")
    private Estilo estilo;
}
