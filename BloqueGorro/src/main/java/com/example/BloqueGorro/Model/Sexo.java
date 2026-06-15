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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Sexo - Gorro")
public class Sexo {

    @Id
    @GeneratedValue
    private Integer id;

    
    @NotBlank (message = "El nombre es obligatorio")
    @Size (min = 1, max = 15, message = "El nombre debe tener entre 1 y 15 caracteres")
    private String nombre;

    //Sexo - gorro
    @OneToMany(mappedBy = "sexo")
    private List<Gorro> gorros;
}

