package com.autenticacionusuario.controller;

import com.autenticacionusuario.security.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";  // Esto muestra login.html
    }

    @PostMapping("/auth/manual-login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpServletResponse response,
                        Model model) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            UserDetails userDetails = (UserDetails) auth.getPrincipal();

            // Obtener el primer rol (por ejemplo, "ROLE_USER")
            String rol = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .findFirst()
                    .orElse("ROLE_USER");

            // Generar token con usuario y rol
            String token = jwtUtil.generarToken(userDetails.getUsername(), rol);

            // Crear cookie con el token JWT
            Cookie cookie = new Cookie("token", token);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            cookie.setMaxAge(24 * 60 * 60);  // 1 día
            response.addCookie(cookie);

            // Redirigir al home si login correcto
            return "redirect:/home";

        } catch (Exception e) {
            model.addAttribute("error", "Credenciales inválidas");
            return "login"; // vuelve a mostrar el login con error
        }
    }
}
