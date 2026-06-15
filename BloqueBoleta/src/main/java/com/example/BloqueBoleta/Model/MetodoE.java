package com.example.BloqueBoleta.Model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name = "MetodoE")
public class MetodoE {
    
    @Id
    @GeneratedValue
    private Integer id;
    
    @NotBlank (message = "El nombre es obligatorio")
    @Size (min = 10, max = 50, message = "El nombre debe tener entre 10 y 50 caracteres")
    private String nombre;

    @NotBlank (message = "El tiempo es obligatorio")
    @Size(min = 1, max = 20, message = "El tiempo debe tener entre 1 y 20 caracteres")
    @Column(nullable = false, length = 20)
    private String tiempo;

    //MetodoEnvio - Boleta
    @OneToMany(mappedBy = "metodoEnvio")
    private List<Boleta> boletas;
}
