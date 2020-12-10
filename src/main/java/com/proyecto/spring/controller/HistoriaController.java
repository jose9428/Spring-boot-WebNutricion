package com.proyecto.spring.controller;

import com.proyecto.spring.models.entity.Antropometrico;
import com.proyecto.spring.models.entity.Cita;
import com.proyecto.spring.models.entity.Paciente;
import com.proyecto.spring.models.entity.Seguimiento;
import com.proyecto.spring.models.service.IAntropometricoService;
import com.proyecto.spring.models.service.ICitaService;
import com.proyecto.spring.models.service.IPacienteService;
import com.proyecto.spring.models.service.ISeguimientoService;
import com.proyecto.spring.util.Utileria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequestMapping("/historia")
public class HistoriaController {

    @Autowired
    public ICitaService citaService;

    @Autowired
    private IAntropometricoService antropService;

    @Autowired
    private ISeguimientoService segService;

    @Autowired
    private IPacienteService pacienteService;

    @GetMapping("/")
    public String MiHistoria() {
        return "/views/Historia";
    }

    @GetMapping("/medico")
    public String CitasHistorialPac(Model model) {
        Paciente p = pacienteService.ObtenerPorUsuario(UsuarioLogeado());
        List<Cita> lista = citaService.ListarCitasPorIdPaciente(p.getId_Paciente(), "ATENDIDO");

        model.addAttribute("paciente", p);
        model.addAttribute("citas", lista);
        return "/views/listar/ListarCitasHistorial";

    }

    @GetMapping("/paciente/{id}")
    public String CitasHistorialPacientes(@PathVariable("id") Long idPaciente, Model model) {
        List<Cita> lista = citaService.ListarCitasPorIdPaciente(idPaciente, "ATENDIDO");

        if (lista.size() > 0) {
            model.addAttribute("paciente", lista.get(0).getPaciente());
            model.addAttribute("citas", lista);
            return "/views/listar/ListarCitasHistorial";
        } else {
            return "redirect:/paciente/";
        }
    }

    @GetMapping("/paciente/detalle/{id}")
    public String DetalleHistoria(Model model, @PathVariable("id") Long idCita) {
        Cita c = citaService.CitaDetalle(idCita, "ATENDIDO");
        if (idCita > 0 && c != null) {
            model.addAttribute("cita", c);
            model.addAttribute("idPaciente", c.getPaciente().getId_Paciente());
            model.addAttribute("idCita", idCita);
            return "/views/HistoriaDetalle";
        }

        return "redirect:/pacientes/";
    }

    @GetMapping("/paciente/antropometrico/{id}")
    public String ListarExamenAntropometrico(Model model, @PathVariable("id") Long idCita) {
        Cita c = citaService.CitaDetalle(idCita, "ATENDIDO");

        if (idCita > 0 && c != null) {
            model.addAttribute("listado", antropService.ListadoPorPaciente(c.getPaciente().getId_Paciente()));
            model.addAttribute("cita", c);
            model.addAttribute("idPaciente", c.getPaciente().getId_Paciente());
            model.addAttribute("idCita", idCita);
            return "/views/listar/ListarExamenAntropometrico";
        }

        return "redirect:/pacientes/";
    }

    @GetMapping("/paciente/dieta/{id}")
    public String ExamenDieta(Model model, @PathVariable("id") Long idCita) {
        Cita c = citaService.CitaDetalle(idCita, "ATENDIDO");

        if (idCita > 0 && c != null) {
            Paciente p = c.getPaciente();
            Antropometrico a = antropService.getById(c.getId_Cita());
            Seguimiento s = segService.getById(c.getId_Cita());

            model.addAttribute("paciente", p);
            model.addAttribute("medico", c.getNutricionista());
            model.addAttribute("antropometrico", a == null ? new Antropometrico() : a);
            model.addAttribute("edad", Utileria.CalcularEdad(p.getFecha_Nacimiento()));
            model.addAttribute("fechaRegistro", c.getFecha_registro());
            model.addAttribute("fecha_proxima_cita", c.getFecha_proxima_cita());
            model.addAttribute("idCita", idCita);
            model.addAttribute("seguimiento", s == null ? new Seguimiento() : s);
            return "/views/ExamenDieta";
        } else {
            return "redirect:/pacientes/";
        }

    }

    @GetMapping("/listarPaciente")
    public String ListarHistoria(Model model) {
        List<Cita> lista = citaService.ListarCitasPorPaciente(UsuarioLogeado(), "Atendido");
        model.addAttribute("citas", lista);

        return "/views/listar/ListarCitasHistorial";
    }

    public String UsuarioLogeado() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        String username = userDetail.getUsername().trim();

        return username;
    }

}
