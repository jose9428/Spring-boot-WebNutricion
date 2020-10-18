package com.proyecto.spring.controller;

import com.proyecto.spring.models.entity.*;
import com.proyecto.spring.models.service.*;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.proyecto.spring.util.Utileria;
import java.util.Base64;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;

@Controller
@RequestMapping("/nutricionista")
@Slf4j
public class NutricionistaController {

    @Autowired
    private INutricionistaService nutricionistaService;

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private ITurnoService turnoService;

    @Autowired
    private IHoraService horaService;

    @Autowired
    private IHorarioNutricionistaService horarioService;

    @GetMapping(value = "/")
    public String ViewNutricionista(Model model) {
        model.addAttribute("nutricionista", new Nutricionista());
        return "/views/MantNutricionista";
    }

    @GetMapping(value = "/horarios")
    public String ViewHorarios(Model model) {
        List<Turno> lista = turnoService.getAll();
        model.addAttribute("listTurno", lista);

        return "/views/MantHorariosNutricionista";
    }

    @GetMapping(value = "/listarHorario")
    public String ListarHorarios(@RequestParam("id") Long id, Model model) {
        model.addAttribute("listaMedicos", horarioService.ListadoNutricionistaPorTurno(id));
        return "/views/listar/ListarHorarioNutricionista";
    }

    @PostMapping("/guardar")
    public ResponseEntity<String> GuardarNutricionista(Nutricionista n,
            @RequestParam("file") MultipartFile imagen, @RequestParam("fechaN") String fecha) {
        try {

            if (!imagen.isEmpty()) {
                n.setFoto(Utileria.ConvertirImagen(imagen));
            }

            if (n.getId_Nutricionista() != 0) {
                 if (nutricionistaService.ExisteCorreoNotId(n.getCorreo(), n.getId_Nutricionista())) {
                    return ResponseEntity.ok("El correo ya se encuentra registrado en el sistema");
                }
                
                
                if (imagen.isEmpty()) {
                    Nutricionista n2 = nutricionistaService.getById(n.getId_Nutricionista());
                    n.setFoto(n2.getFoto());
                }
            } else {
                // Nuevo Medico
                if (nutricionistaService.ExisteCorreo(n.getCorreo())) {
                    return ResponseEntity.ok("El correo ya se encuentra registrado en el sistema");
                }
            }

            Long idUser = nutricionistaService.ObtenerIdUsuario(n.getId_Nutricionista());
            Usuario user = null;
            //Nuevo Usuario
            if (idUser == null) {
                Perfil p = new Perfil();
                p.setId_Perfil(2L);
                user = new Usuario();
                user.setUsername(nutricionistaService.UsuarioGenerado());
                user.setPass(n.getDni());
                user.setFecha_Registro(new Date());
                user.setEstado(1);
                user.setPerfil(p);
            } else {
                user = usuarioService.getById(idUser);
            }

            n.setFecha_Nacimiento(Utileria.ConvertirFecha(fecha));
            n.setUsuario(user);

            nutricionistaService.Guardar(n);

            return ResponseEntity.ok("OK");

        } catch (Exception ex) {
            return new ResponseEntity<String>("ERROR", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/Buscar/{id}")
    public ResponseEntity<Nutricionista> Buscar(@PathVariable("id") Long id) {

        Nutricionista n = nutricionistaService.getById(id);

        return ResponseEntity.ok(n);
    }

    @PostMapping("/ActualizarEstado")
    public ResponseEntity<String> ActualizarEstado(@RequestParam Long usuario,
            @RequestParam int estado) {
        int res = 0;

        try {
            nutricionistaService.ActualizarEstado(usuario, estado);
            res = 1;
        } catch (Exception ex) {
        }
        return ResponseEntity.ok(String.valueOf(res));
    }

    @GetMapping("/Listar")
    public String Listar(Model model) {
        List<Nutricionista> lista = nutricionistaService.getAll();
        model.addAttribute("listaMedicos", lista);
        return "/views/listar/ListarNutricionista";
    }

    @GetMapping("/verImagen/{id}")
    @ResponseBody
    public void showImage(@PathVariable("id") Long id, HttpServletResponse response, Nutricionista nutric)
            throws ServletException, IOException {

        nutric = nutricionistaService.getById(id);
        response.setContentType("image/jpeg, image/jpg, image/png, image/gif");

        if (nutric.getFoto() != null) {
            response.getOutputStream().write(nutric.getFoto());
        }

        response.getOutputStream().close();
    }

    @GetMapping("/verImagenAjax/{id}")
    @ResponseBody
    public String showImageAjax(@PathVariable("id") Long id) {
        String imagen = "";
        Nutricionista n = nutricionistaService.getById(id);

        if (n.getFoto() != null) {
            imagen = Base64.getEncoder().encodeToString(n.getFoto());
        }

        return imagen;
    }

    @GetMapping(value = "/listarMedicosDisp")
    @ResponseBody
    public List ListaMedicosDispTurnos(@Param("id") Long id) {
        return horarioService.ListadoNutricionistaDispTurno(id);
    }

    @PostMapping(value = "/GuardarHorario")
    @ResponseBody
    public ResponseEntity<String> GuardarHorario(@RequestParam("Turno2") Long idTurno, @RequestParam("medico") Long idMedico) {

        Turno t = turnoService.getById(idTurno);
        Nutricionista n = nutricionistaService.getById(idMedico);

        if (t != null && n != null) {
            HorarioNutricionista h = new HorarioNutricionista();
            h.setNutricionista(n);
            h.setTurno(t);
            horarioService.Guardar(h);

            return ResponseEntity.ok("OK");
        } else {
            return ResponseEntity.ok("No se ha podido guardar el horario paar el nutricionista.");
        }

    }
}
