package com.autenticacionusuario;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderTest {
    public static void main(String[] args) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();

        String rawPassword = "123456";
        String encodedPassword = encoder.encode(rawPassword);

        System.out.println("Contraseña sin encriptar: " + rawPassword);
        System.out.println("Contraseña encriptada: " + encodedPassword);

        // Verificación
        boolean match = encoder.matches(rawPassword, encodedPassword);
        System.out.println("¿Coincide?: " + match);
    }
}

