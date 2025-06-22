package com.autenticacionusuario.service;

import com.autenticacionusuario.model.*;
import com.autenticacionusuario.repository.*;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Service
public class BlockchainService {

    @Autowired
    private TransaccionRepository transaccionRepository;

    @Autowired
    private BloqueRepository bloqueRepository;

    @Autowired
    private MinerRepository minerRepository;

    @Autowired
    private GananciaRepository gananciaRepository;

    @Autowired
    private UserDetailRepository userDetailRepository;

    @Autowired
    private EmailService emailService;

    private List<Transaccion> transaccionesPendientes = new ArrayList<>();

    private static final double RECOMPENSA_MINERO = 10.0;

    public String procesarTransferencia(Long idOrigen, Long idDestino, double monto) {
        Optional<UserDetail> emisorOpt = userDetailRepository.findById(idOrigen);
        Optional<UserDetail> receptorOpt = userDetailRepository.findById(idDestino);

        if (emisorOpt.isEmpty() || receptorOpt.isEmpty()) {
            return "Emisor o receptor no existe.";
        }

        UserDetail emisor = emisorOpt.get();
        UserDetail receptor = receptorOpt.get();

        if (emisor.getSaldo().compareTo(BigDecimal.valueOf(monto)) < 0) {
            return "Saldo insuficiente.";
        }

        Transaccion tx = new Transaccion();
        tx.setId1(idOrigen);
        tx.setId2(idDestino);
        tx.setCantidad(monto);
        tx.setFecha(LocalDate.now());
        tx.setHora(LocalTime.now());
        tx.setEstado("PENDIENTE");

        transaccionesPendientes.add(tx);
        transaccionRepository.save(tx);

        emisor.setSaldo(emisor.getSaldo().subtract(BigDecimal.valueOf(monto)));
        receptor.setSaldo(receptor.getSaldo().add(BigDecimal.valueOf(monto)));
        userDetailRepository.save(emisor);
        userDetailRepository.save(receptor);

        // Notificar a emisor y receptor
        String asunto = "Transferencia registrada";
        String cuerpo = "<h3>Transferencia registrada</h3>"
                + "<p>De: " + emisor.getNomCompleto() + " (" + emisor.getUsername() + ")</p>"
                + "<p>Para: " + receptor.getNomCompleto() + " (" + receptor.getUsername() + ")</p>"
                + "<p>Monto: S/." + monto + "</p>"
                + "<p>Fecha: " + LocalDate.now() + " " + LocalTime.now() + "</p>";

        emailService.enviarCorreo(emisor.getEmail(), asunto, cuerpo);
        emailService.enviarCorreo(receptor.getEmail(), asunto, cuerpo);

        if (transaccionesPendientes.size() == 3) {
            cerrarBloque(transaccionesPendientes);
            transaccionesPendientes.clear();
        }

        return "Transferencia registrada exitosamente.";
    }

    private void cerrarBloque(List<Transaccion> transacciones) {
        List<Miner> mineros = minerRepository.findAll();
        if (mineros.isEmpty()) return;

        Miner minero = mineros.get(new Random().nextInt(mineros.size()));

        Bloque bloque = new Bloque();
        bloque.setFecha(LocalDate.now());
        bloque.setHora(LocalTime.now());

        StringBuilder contenido = new StringBuilder();
        for (Transaccion tx : transacciones) {
            contenido.append("TX:")
                    .append(tx.getId()).append("-")
                    .append(tx.getId1()).append("→")
                    .append(tx.getId2()).append(":")
                    .append(tx.getCantidad()).append("\n");
            tx.setEstado("CONFIRMADA");
            tx.setBloque(bloque);
        }

        bloque.setDatos(contenido.toString());
        bloque.setHash(UUID.randomUUID().toString());
        bloque.setEstado("CERRADA");
        bloqueRepository.save(bloque);
        transaccionRepository.saveAll(transacciones);

        Ganancia ganancia = new Ganancia();
        ganancia.setBloque(bloque);
        ganancia.setMinero(minero);
        ganancia.setGanancia(RECOMPENSA_MINERO);
        ganancia.setFecha(LocalDate.now());
        ganancia.setHora(LocalTime.now());
        gananciaRepository.save(ganancia);
    }

    public List<Transaccion> obtenerTodas() {
        return transaccionRepository.findAll();
    }

    public List<Ganancia> obtenerGanancias() {
        return gananciaRepository.findAll();
    }
}
