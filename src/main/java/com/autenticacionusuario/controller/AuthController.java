package com.autenticacionusuario.controller;

import com.autenticacionusuario.dto.LoginRequest;
import com.autenticacionusuario.model.UserDetail;
import com.autenticacionusuario.repository.UserDetailRepository;
import com.autenticacionusuario.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailRepository userRepository;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody LoginRequest request) {
        try {


            Authentication autenticacion = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            System.out.println("Autenticación exitosa");


            User user = (User) autenticacion.getPrincipal();
            String token = jwtUtil.generarToken(user.getUsername(), user.getAuthorities().iterator().next().getAuthority());

            // Extraer datos adicionales desde la tabla.
            UserDetail userDetail = userRepository.findByUsername(user.getUsername()).get();

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("username", user.getUsername());
            response.put("rol",userDetail.getRol());
            response.put("nomcompleto", userDetail.getNomCompleto());
            response.put("email", userDetail.getEmail());

            return response;
        } catch (Exception e) {
            throw new RuntimeException("Error en login: " + e.getMessage());
        }
    }

    @GetMapping("/users/me")
    public ResponseEntity<?> getUserProfile(Authentication authentication) {
        System.out.println("Entró al controlador");
        String username = authentication.getName();
        UserDetail user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        Map<String, Object> profile = new HashMap<>();
        profile.put("username", user.getUsername());
        profile.put("email", user.getEmail());
        profile.put("rol", user.getRol());
        profile.put("nomcompleto", user.getNomCompleto());
        profile.put("saldo", user.getSaldo());
        return ResponseEntity.ok(profile);
    }
}
