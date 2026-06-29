package com.example.BloqueGorro.Model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Entity(name = "Material")

public class Material {

    @Id
    @GeneratedValue
    private Integer id;

    @NotBlank (message = "El nombre es obligatorio")
    @Size (min = 10, max = 50, message = "El nombre debe tener entre 10 y 50 caracteres")
    private String nombre;

    //Material - gorro
    @OneToMany(mappedBy = "material")
    private List<Materiales> materiales;
}

