package com.proyecto.spring.controller;

import com.proyecto.spring.models.entity.Nutricionista;
import com.proyecto.spring.models.service.INutricionistaService;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    @Autowired
    private INutricionistaService nutricionistaService;

    @GetMapping("/")
    public String Index() {
        return "/views/index";
    }
    
    @GetMapping("/login")
    public String IniciarSesion() {
        return "/views/Login";
    }

    @GetMapping("/staff")
    public String StaffMedicos(Model model, @RequestParam(required = false) Integer page) {

        if (page == null || page == 0) {
            page = 0;
        } else {
            page = page - 1;
        }

        PageRequest pageRequest = PageRequest.of(page, 3);
        Page<Nutricionista> lista = nutricionistaService.getListDisponibles(pageRequest);
        int totalPage = lista.getTotalPages();

        if (totalPage > 0) {
            List<Integer> pages = IntStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());
            model.addAttribute("pages", pages);
        }

        model.addAttribute("medicos", lista.getContent());
        model.addAttribute("actual", page + 1); // current
        model.addAttribute("siguiente", page + 2); // next
        model.addAttribute("anterior", page); // prev
         model.addAttribute("ultimo", totalPage); // last
        return "/views/staff";
    }
}
