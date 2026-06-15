package com.example.BloqueBoleta.DTO;

import java.time.LocalDate;

import lombok.Data;

@Data
public class BoletaDTO {
    private Integer id;
    private LocalDate fecha;
    private String metodoEnvio;
    private String metodoPago;

    private ClienteDTOExterno cliente;
    private GorroDTOExterno gorro;
}
