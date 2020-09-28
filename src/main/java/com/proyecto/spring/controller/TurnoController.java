package com.proyecto.spring.controller;

import com.proyecto.spring.models.entity.Turno;
import com.proyecto.spring.models.service.ITurnoService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/turno")
@Slf4j
public class TurnoController {

    @Autowired
    private ITurnoService turnoService;

    @GetMapping("/listar")
    @ResponseBody
    public List<Turno> ListaTurnos() {
        return turnoService.getAll();
    }
}
