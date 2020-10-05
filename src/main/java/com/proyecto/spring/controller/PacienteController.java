package com.proyecto.spring.controller;

import com.proyecto.spring.models.service.IContexturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/paciente")
public class PacienteController {
    
    @Autowired
    private IContexturaService contexturaService;
    
    @GetMapping("/registrar")
    public String AddPaciente(Model model){
        model.addAttribute("contexturas", contexturaService.getAll());
        return "/views/NuevoPaciente";
    }
}
