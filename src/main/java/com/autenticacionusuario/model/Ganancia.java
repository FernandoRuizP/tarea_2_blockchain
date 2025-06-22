package com.autenticacionusuario.model;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
public class Ganancia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idmin")
    private Miner minero;

    @ManyToOne
    @JoinColumn(name = "idbloque")
    private Bloque bloque;

    private Double ganancia;
    private LocalDate fecha;
    private LocalTime hora;
}
