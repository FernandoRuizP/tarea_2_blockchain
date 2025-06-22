package com.autenticacionusuario.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
public class Transaccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fecha;
    private LocalTime hora;

    private Long id1; // emisor
    private Long id2; // receptor
    private Double cantidad;
    private String estado;

    @ManyToOne
    @JoinColumn(name = "id_bloque")
    private Bloque bloque;
}
