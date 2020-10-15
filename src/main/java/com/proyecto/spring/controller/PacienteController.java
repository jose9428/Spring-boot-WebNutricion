package com.proyecto.spring.controller;

import com.proyecto.spring.models.entity.Contextura;
import com.proyecto.spring.models.entity.ErrorEntity;
import com.proyecto.spring.models.entity.Paciente;
import com.proyecto.spring.models.entity.Perfil;
import com.proyecto.spring.models.entity.Usuario;
import com.proyecto.spring.models.service.IContexturaService;
import com.proyecto.spring.models.service.IPacienteService;
import com.proyecto.spring.util.Utileria;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/paciente")
public class PacienteController {

    @Autowired
    private IContexturaService contexturaService;

    @Autowired
    private IPacienteService pacienteService;

    @Autowired
    private JavaMailSender mailSender;

    @GetMapping("/registrar")
    public String AddPaciente(Model model) {
        model.addAttribute("contexturas", contexturaService.getAll());
        return "/views/NuevoPaciente";
    }

    @PostMapping("/guardar")
    public ResponseEntity<Object> GuardarPaciente(@Valid Paciente p, BindingResult errores,
            @RequestParam("fechaNac") String fechaNac, @RequestParam("id_contextura") Long id_contextura) {

        try {
            Set<ErrorEntity> lista = null;
            Date fechaNacimiento = Utileria.ConvertirFecha(fechaNac);

            if (errores.hasErrors()) {
                lista = Utileria.getListError(errores);
            }

            if (fechaNacimiento == null || lista != null) {
                if (lista == null) {
                    lista = new HashSet<>();
                }

                if (fechaNacimiento == null) {
                    lista.add(new ErrorEntity("fechaNac", "El campo de la fecha de nacimiento es requerido."));
                }

                return ResponseEntity.accepted().body(lista); // 202
            }

            Perfil per = new Perfil();
            per.setId_Perfil(3L);

            Contextura c = contexturaService.BuscarPorId(id_contextura);
            p.setFecha_Nacimiento(fechaNacimiento);
            p.getUsuario().setFecha_Registro(new Date());
            p.getUsuario().setPerfil(per);
            p.setContextura(c);

            pacienteService.Guardar(p);
            return ResponseEntity.ok("OK"); // 200

        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("A ocurrido un error al momento de procesar la info : " + ex.getMessage()); // 400
        }
    }

    @GetMapping("/enviarToken")
    public ResponseEntity<Object> EnviarCorreo(@RequestParam String correo) {
        String destinatario = correo;
        String token = Utileria.CodigoToken();

        String asunto = "Codigo de verificacion";
        String mensaje = "Hemos recibido tu token : " + token;
        try {
            SimpleMailMessage email = new SimpleMailMessage();

            email.setTo(destinatario);
            email.setSubject(asunto);
            email.setText(mensaje);

            mailSender.send(email);

            return ResponseEntity.ok("OK"); // 200
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("No se ha podido enviar el token al correo " + correo + " , info : " + ex.getMessage()); // 400
        }
    }

    @GetMapping("/activarCuenta")
    public ResponseEntity<Object> ActivarCuenta(@RequestParam String correo,
            @RequestParam String token) {

        try {

            return ResponseEntity.ok("OK"); // 200
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("No se ha podido activar la cuenta. Mas informacion : " + ex.getMessage()); // 400
        }

    }
}
