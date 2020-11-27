package com.proyecto.spring.controller;

import com.proyecto.spring.models.entity.Alimentos;
import com.proyecto.spring.models.entity.Contextura;
import com.proyecto.spring.models.service.IAlimentosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/alimentos")
public class AlimentosController {

    @Autowired
    private IAlimentosService alimentosService;

    @GetMapping("/")
    public String ViewAlimentos(Model model) {
        model.addAttribute("alimentos", new Alimentos());
        return "/views/MantAlimentos";
    }

    @GetMapping("/listar")
    public String ListaAlimentos(Model model) {
        model.addAttribute("listado", alimentosService.getAll());
        return "/views/listar/ListarAlimentos";
    }
    
     @PostMapping(value = "/guardar")
    public ResponseEntity<String> Guardar(Alimentos a) {
        try {
            int count;
            if (a.getId_Alimento() == 0) {
                count = alimentosService.CantidadPorNombre(a.getNombre_Alimento());
            } else {
                count = alimentosService.CantidadPorNombreYId(a.getNombre_Alimento() , a.getId_Alimento());
            }

            if (count == 0) {
                alimentosService.GuardarAlimentos(a);
                return ResponseEntity.ok("OK");
            } else {
                return ResponseEntity.ok("No se ha podido guardar datos del alimento, ya que ya se encuentra registrado.");
            }

        } catch (Exception ex) {
            return new ResponseEntity<String>("ERROR", HttpStatus.BAD_REQUEST);
        }
    }
    
        @GetMapping("/Buscar/{id}")
    public ResponseEntity<Alimentos> Buscar(@PathVariable("id") Long id) {

        Alimentos c = alimentosService.BuscarPorId(id);

        return ResponseEntity.ok(c);
    }

}
