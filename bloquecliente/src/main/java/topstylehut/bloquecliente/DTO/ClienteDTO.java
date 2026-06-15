package topstylehut.bloquecliente.DTO;



import lombok.Data;

@Data
public class ClienteDTO {
    private Integer id;
    private String nombre;
    private String comuna;
    private String region;
    private String direccion;
    
}
