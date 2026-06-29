package com.example.BloqueBoleta.Model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "boletas")
public class Boleta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;

    //Boleta - MetodoPago
    @ManyToOne
    @JoinColumn(name = "metodo_pago_id")
    private MetodoPago metodoPago;

    //Boleta - MetodoEnvio
    @ManyToOne
    @JoinColumn(name = "metodo_envio_id", nullable = false)
    private MetodoE metodoEnvio;

    //conectar con gorro
    @NotNull(message = "el gorro siempre debe existir")
    private Integer gorroId;
    
    //conectar con Cliente
    @NotNull(message = "el cliente siempre debe estar")
    private Integer clienteId;
}
