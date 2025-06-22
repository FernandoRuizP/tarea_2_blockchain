package com.autenticacionusuario.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home() {
        return "home"; // Debe existir src/main/resources/templates/home.html
    }
}
