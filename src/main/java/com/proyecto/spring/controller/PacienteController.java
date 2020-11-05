package com.proyecto.spring.controller;

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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

}
