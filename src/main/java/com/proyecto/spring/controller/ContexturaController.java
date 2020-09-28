package com.proyecto.spring.controller;

import com.proyecto.spring.models.entity.Contextura;
import com.proyecto.spring.models.service.IContexturaService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/contextura")
@Slf4j
public class ContexturaController {

    @Autowired
    private IContexturaService contexturaService;

    @GetMapping("/")
    public String ViewContextura(Model model) {
        model.addAttribute("contextura", new Contextura());
        return "/views/MantContextura";
    }

    @PostMapping(value = "/guardar")
    public ResponseEntity<String> GuardarContextura(Contextura c) {
        try {
            int count;
            if (c.getId_contextura() == 0) {
                count = contexturaService.CantidadPorNombre(c.getNombreContextura());
            } else {
                count = contexturaService.CantidadPorNombreYId(c.getNombreContextura(), c.getId_contextura());
            }

            if (count == 0) {
                contexturaService.GuardarContextura(c);
                return ResponseEntity.ok("OK");
            } else {
                return ResponseEntity.ok("No se ha podido guardar datos de la contextura, ya que ya se encuentra registrado.");
            }

        } catch (Exception ex) {
            return new ResponseEntity<String>("ERROR", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/Buscar/{id}")
    public ResponseEntity<Contextura> Buscar(@PathVariable("id") Long id) {

        Contextura c = contexturaService.BuscarPorId(id);

        return ResponseEntity.ok(c);
    }

    @GetMapping("/Listar")
    public String Listar(Model model) {
        List<Contextura> lista = contexturaService.getAll();
        model.addAttribute("listado", lista);
        return "/views/listar/ListarContextura";
    }
}
