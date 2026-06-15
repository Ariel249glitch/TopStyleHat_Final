package com.example.BloqueGorro.Model;

import java.util.List;

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
@Table(name = "Estilo")
public class Estilo {
    @Id
    @GeneratedValue
    private Integer id;
    
    @NotBlank (message = "El nombre es obligatorio")
    @Size (min = 10, max = 50, message = "El nombre debe tener entre 10 y 50 caracteres")
    private String nombre;

    //Estilo - gorro
    @OneToMany(mappedBy = "estilo")
    private List<Estilos> estilos;
}