package com.proyecto.spring.controller;

import com.proyecto.spring.models.service.IAntropometricoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/antropometrico")
public class AntropometricoController {
    
    @Autowired
    private IAntropometricoService antropService;
    
    @GetMapping("/guardar")
    @ResponseBody
    public String Guardar(){
        
        return "OK";
    }
}
