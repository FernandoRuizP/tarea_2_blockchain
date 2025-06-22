package com.autenticacionusuario.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "userdetail")
@Data
public class UserDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    @Column(name = "password")
    private String password;
    private String rol;
    @Column(name = "nomcompleto")
    private String nomCompleto;
    private String dni;
    private BigDecimal saldo;
    @Column(name = "firmadigital")
    private String firmaDigital;
    private String email;
}
