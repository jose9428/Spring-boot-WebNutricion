package com.proyecto.spring.controller;

import com.proyecto.spring.models.service.INutricionistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    
    @Autowired
    private INutricionistaService nutricionistaService;
    
    @GetMapping("/")
    public String Index(){
        return "/views/index";
    }
    
     @GetMapping("/staff")
    public String StaffMedicos(Model model){
        model.addAttribute("medicos", nutricionistaService.getAll());
        return "/views/staff";
    }
}
