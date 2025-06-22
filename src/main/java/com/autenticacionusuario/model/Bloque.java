package com.autenticacionusuario.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Data
public class Bloque {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fechaInicio = LocalDateTime.now();
    private LocalDateTime fechaCierre;

    private String hash;
    private String hashAnterior;
    private String estado = "ABIERTO";


    // Campos nuevos necesarios para BlockchainService
    private LocalDate fecha;
    private LocalTime hora;
    private String datos;
}