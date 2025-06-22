package com.autenticacionusuario.controller;

import com.autenticacionusuario.model.Ganancia;
import com.autenticacionusuario.service.BlockchainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminGananciaController {

    @Autowired
    private BlockchainService blockchainService;

    @GetMapping("/ganancias")
    public String verGanancias(Model model) {
        List<Ganancia> ganancias = blockchainService.obtenerGanancias();
        model.addAttribute("ganancias", ganancias);
        return "admin-ganancias";
    }
}
