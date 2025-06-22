package com.autenticacionusuario.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Miner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String dni;
    private String clave;
}