package com.example.BloqueGorro.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Gorros")

public class Gorros {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @NotBlank (message = "El nombre es obligatorio")
    @Size (min = 10, max = 50, message = "El nombre debe tener entre 10 y 50 caracteres")
    @Column(nullable = false, length = 50)
    private String nombre;

    @NotBlank (message = "La talla es obligatoria")
    @Size(min = 1, max = 3, message = "La talla debe tener entre 1 y 3 caracteres")
    @Column(nullable = false, length = 3)
    private String talla;

    @NotNull(message = "El precio es obligatorio")
    @Min(value = 1, message = "el precio minimo es ")
    @Max(value = 5, message = "El valor maximo es")
    private Integer precio;

    

}

