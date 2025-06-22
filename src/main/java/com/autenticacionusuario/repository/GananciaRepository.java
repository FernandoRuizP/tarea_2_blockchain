package com.autenticacionusuario.repository;

import com.autenticacionusuario.model.Ganancia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GananciaRepository extends JpaRepository<Ganancia, Long> {
}