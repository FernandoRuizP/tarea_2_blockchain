package com.autenticacionusuario.controller;

import com.autenticacionusuario.service.BlockchainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller  // <--- aquí está el cambio
@RequestMapping("/transferencia")
public class TransferenciaController {

    @Autowired
    private BlockchainService blockchainService;

    @GetMapping
    public String mostrarFormulario() {
        return "transferencia"; // src/main/resources/templates/transferencia.html
    }

    @PostMapping
    public String realizarTransferencia(
            @RequestParam Long idOrigen,
            @RequestParam Long idDestino,
            @RequestParam double monto,
            Model model) {

        String resultado = blockchainService.procesarTransferencia(idOrigen, idDestino, monto);

        model.addAttribute("mensaje", resultado);
        model.addAttribute("idOrigen", idOrigen);
        model.addAttribute("idDestino", idDestino);
        model.addAttribute("monto", monto);

        return "transferencia"; // vuelve a mostrar el formulario con mensaje
    }
}
