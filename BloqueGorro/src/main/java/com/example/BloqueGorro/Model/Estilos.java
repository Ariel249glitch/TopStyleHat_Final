package com.example.BloqueGorro.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Estilos")
public class Estilos {
    @Id
    @GeneratedValue
    private Integer id;
    
    @NotBlank (message = "El nombre es obligatorio")
    @Size (min = 10, max = 50, message = "El nombre debe tener entre 10 y 50 caracteres")
    private String nombre;

    //Estilos - Gorro
    @ManyToOne
    @JoinColumn(name = "gorro_id")
    private Gorro gorro;

    //Estilos - estilo
    @ManyToOne
    @JoinColumn(name = "estilo_id")
    private Estilo estilo;
}
