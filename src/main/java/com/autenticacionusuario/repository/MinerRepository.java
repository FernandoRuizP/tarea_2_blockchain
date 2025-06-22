package com.autenticacionusuario.repository;

import com.autenticacionusuario.model.Miner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MinerRepository extends JpaRepository<Miner, Long> {}