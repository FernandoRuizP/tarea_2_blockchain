package com.autenticacionusuario.repository;

import com.autenticacionusuario.model.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {
    List<Transaccion> findByEstado(String estado);
}
