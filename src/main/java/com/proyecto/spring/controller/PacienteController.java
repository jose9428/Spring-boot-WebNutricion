package com.proyecto.spring.controller;

import com.proyecto.spring.models.entity.Administrador;
import com.proyecto.spring.models.entity.Contextura;
import com.proyecto.spring.models.entity.ErrorEntity;
import com.proyecto.spring.models.entity.Paciente;
import com.proyecto.spring.models.entity.Perfil;
import com.proyecto.spring.models.entity.Usuario;
import com.proyecto.spring.models.service.IContexturaService;
import com.proyecto.spring.models.service.IPacienteService;
import com.proyecto.spring.util.Utileria;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/paciente")
public class PacienteController {

    @Autowired
    private IPacienteService pacienteService;

    @Autowired
    private JavaMailSender mailSender;

    @GetMapping(value = "/")
    public String ViewPaciente(Model model) {
        return "/views/HistoriaPacientes";
    }

    @GetMapping("/config")
    public String CambiarClave(Model model) {
        return "/views/CambiarClave";
    }

    @GetMapping("/perfil")
    public String MiPerfil(Model model) {
        Paciente paciente = pacienteService.ObtenerPorUsuario(UsuarioLogeado());

        if (paciente == null) {
            return "redirect:/paciente";
        } else {

            model.addAttribute("paciente", paciente);
            return "/views/Perfil";
        }
    }

    @PostMapping("/guardar")
    public ResponseEntity<Object> GuardarAdministrador(@Valid Paciente paciente, BindingResult errores,
            @RequestParam("file") MultipartFile imagen, @RequestParam("fechaN") String fecha) {

        try {

            if (errores.hasErrors()) {
                Set<ErrorEntity> lista = Utileria.getListError(errores);

                return ResponseEntity.accepted().body(lista); // 202
            }

            if (paciente.getId_Paciente() == null) {
                if (pacienteService.ExisteCorreo(paciente.getCorreo())) {
                    return ResponseEntity.ok("El correo ya se encuentra registrado en el sistema");
                }
            }

            if (paciente.getId_Paciente() != null) {
                Paciente pacAux = pacienteService.getById(paciente.getId_Paciente());
                paciente.setFoto(pacAux.getFoto());
                paciente.setUsuario(pacAux.getUsuario());
            }

            if (!imagen.isEmpty()) {
                paciente.setFoto(Utileria.ConvertirImagen(imagen));
            }

            paciente.setFecha_Nacimiento(Utileria.ConvertirFecha(fecha));

            pacienteService.Guardar(paciente);

            return ResponseEntity.ok("OK"); // 200

        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("A ocurrido un error al momento de procesar la info : " + ex); // 400
        }
    }

    @GetMapping(value = "/listarPacientes")
    public String ListadoPacientes(Model model) {
        model.addAttribute("listaPacientes", pacienteService.getAll());
        return "/views/listar/ListarPacientes";
    }

    @GetMapping("/detallePaciente")
    @ResponseBody
    public Paciente DetallePaciente(@RequestParam("codigo") Long codigo) {
        return pacienteService.ObtenerPorIdPaciente(codigo);
    }

    @GetMapping("/verImagen/{id}")
    @ResponseBody
    public void showImage(@PathVariable("id") Long id, HttpServletResponse response, Paciente paciente)
            throws ServletException, IOException {

        paciente = pacienteService.getById(id);
        response.setContentType("image/jpeg, image/jpg, image/png, image/gif");

        if (paciente.getFoto() != null) {
            response.getOutputStream().write(paciente.getFoto());
        }

        response.getOutputStream().close();
    }

    public String UsuarioLogeado() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        String username = userDetail.getUsername().trim();

        return username;
    }

}
