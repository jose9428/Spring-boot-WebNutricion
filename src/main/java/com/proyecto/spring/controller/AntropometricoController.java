package com.proyecto.spring.controller;

import com.proyecto.spring.models.entity.Antropometrico;
import com.proyecto.spring.models.service.IAntropometricoService;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/antropometrico")
public class AntropometricoController {

    @Autowired
    private IAntropometricoService antropService;

    @GetMapping("/guardar")
    @ResponseBody
    public String Guardar(Antropometrico a) {
        a.setFecha_atencion(new Date());

        try {
            antropService.Guardar(a);
            
            return "OK";
        } catch (Exception ex) {
            return "No se ha podido guardar los datos de prueba de antropometricos.";
        }

    }
    
    @GetMapping("/listarPorPaciente")
    public String ListarPorPaciente(@RequestParam("idPaciente") Long idPaciente , Model model){
        model.addAttribute("listado", antropService.ListadoPorPaciente(idPaciente));
        return "/views/listar/ListarAntropometrico";
    }
}
