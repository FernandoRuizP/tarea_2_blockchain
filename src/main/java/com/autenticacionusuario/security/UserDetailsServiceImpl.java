package com.autenticacionusuario.security;

import com.autenticacionusuario.model.UserDetail;
import com.autenticacionusuario.repository.UserDetailRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserDetailRepository repositorio;

    public UserDetailsServiceImpl(UserDetailRepository repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Intentando cargar usuario: " + username);

        UserDetail user = repositorio.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        System.out.println("Usuario encontrado: " + user.getUsername());
        System.out.println("Contraseña en BD (encriptada): " + user.getPassword());
        System.out.println("Rol: " + user.getRol());

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRol()))
        );

    }
}
