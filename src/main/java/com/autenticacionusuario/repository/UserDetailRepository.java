package com.autenticacionusuario.repository;

import com.autenticacionusuario.model.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDetailRepository extends JpaRepository<UserDetail, Long> {
    Optional<UserDetail> findByUsername(String username);
}
