package com.autenticacionusuario.controller;

import com.autenticacionusuario.model.UserDetail;
import com.autenticacionusuario.repository.UserDetailRepository;
import com.autenticacionusuario.service.BlockchainService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/transferencia")
public class TransferenciaController {

    @Autowired
    private BlockchainService blockchainService;

    @Autowired
    private UserDetailRepository userDetailRepository; 

    @GetMapping
    public String mostrarFormulario(Model model, Principal principal) {
        UserDetail usuario = userDetailRepository.findByUsername(principal.getName()).orElseThrow();
        model.addAttribute("saldo", usuario.getSaldo());
        return "transferencia";
    }

    @PostMapping
    public String realizarTransferencia(
            @RequestParam Long idDestino,
            @RequestParam double monto,
            Model model,
            Principal principal) {

        UserDetail usuario = userDetailRepository.findByUsername(principal.getName()).orElseThrow();
        Long idOrigen = usuario.getId();

        String resultado = blockchainService.procesarTransferencia(idOrigen, idDestino, monto);

        model.addAttribute("mensaje", resultado);
        model.addAttribute("idDestino", idDestino);
        model.addAttribute("monto", monto);
        model.addAttribute("saldo", usuario.getSaldo());

        return "transferencia";
    }
}
