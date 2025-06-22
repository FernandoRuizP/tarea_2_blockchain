package com.autenticacionusuario.service;



import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void enviarCorreo(String destino, String asunto, String contenido) {
        try {
            MimeMessage mensaje = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");

            helper.setTo(destino);
            helper.setSubject(asunto);
            helper.setText(contenido, true); // true para habilitar HTML

            mailSender.send(mensaje);
        } catch (MessagingException e) {
            // Manejo básico de error. En producción, usar logging.
            System.err.println("Error al enviar correo: " + e.getMessage());
        }
    }
}

