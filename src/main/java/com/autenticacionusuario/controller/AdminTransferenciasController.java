package com.autenticacionusuario.controller;

import com.autenticacionusuario.model.Transaccion;
import com.autenticacionusuario.service.BlockchainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminTransferenciasController {

    @Autowired
    private BlockchainService blockchainService;

    @GetMapping("/transferencias")
    public String verTransferencias(Model model) {
        List<Transaccion> transferencias = blockchainService.obtenerTodas();
        model.addAttribute("transferencias", transferencias);
        return "admin-transferencias"; // Asegúrate que este archivo exista en templates
    }
}