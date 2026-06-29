package topstylehut.bloquecliente.Model;

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
@Table(name = "regiones")
public class Region {
    
    @Id
    @GeneratedValue
    private Integer id;
    
    @NotBlank (message = "El nombre es obligatorio")
    @Size (min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres")
    private String nombre;

    //Region - Cliente
    @OneToMany(mappedBy = "region")
    private List<Comuna> comunas;
    
}

