package com.proyecto.spring.controller;

import com.proyecto.spring.models.entity.*;
import com.proyecto.spring.models.service.*;
import com.proyecto.spring.util.Utileria;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin")
@Slf4j
public class AdministradorController {

    @Autowired
    private IAdministradorService adminService;

    @GetMapping("/")
    public String ViewAdmin(Model model) {
        model.addAttribute("admin", new Administrador());
        return "/views/MantAdmin";
    }

    @GetMapping("/listar")
    public String Listar(Model model) {
        model.addAttribute("listado", adminService.getAll());
        return "/views/listar/ListarAdmin";
    }

    @GetMapping("/verImagen/{id}")
    @ResponseBody
    public void showImage(@PathVariable("id") Long id, HttpServletResponse response, Administrador admin)
            throws ServletException, IOException {

        admin = adminService.getById(id);
        response.setContentType("image/jpeg, image/jpg, image/png, image/gif");

        if (admin.getFoto() != null) {
            response.getOutputStream().write(admin.getFoto());
        }

        response.getOutputStream().close();
    }

    @PostMapping("/guardar")
    public ResponseEntity<Object> GuardarNutricionista(@Valid Administrador a, BindingResult errores,
            @RequestParam("file") MultipartFile imagen, @RequestParam("fechaN") String fecha) {
        
        try {

            if (errores.hasErrors()) {
                List<ErrorEntity> lista = Utileria.getListError(errores);
              
                return ResponseEntity.accepted().body(lista); // 202
            }

            if (!imagen.isEmpty()) {
                a.setFoto(Utileria.ConvertirImagen(imagen));
            }

            Usuario user = null;
            Perfil p = new Perfil();
            p.setId_Perfil(1L);
            user = new Usuario();
            user.setUsername(adminService.UsuarioGenerado());
            user.setPass(a.getDni());
            user.setFecha_Registro(new Date());
            user.setEstado(1);
            user.setPerfil(p);

            a.setFecha_Nacimiento(Utileria.ConvertirFecha(fecha));
            a.setUsuario(user);

            adminService.Guardar(a);

            return ResponseEntity.ok("OK"); // 200

        } catch (Exception ex) {
             return  ResponseEntity.badRequest().body("A ocurrido un error al momento de procesar la info : "+ex.getMessage()); // 400
        }
    }
}