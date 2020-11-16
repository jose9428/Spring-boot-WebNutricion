package com.proyecto.spring.controller;

import com.proyecto.spring.models.entity.Usuario;
import com.proyecto.spring.models.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;
    
    @GetMapping("/cambiar")
    @ResponseBody
    public String CambiarClave(@RequestParam("nueva") String nueva , @RequestParam("actual") String actual) {
        String cadena = "";
        Usuario user = usuarioService.getByUsuario(UsuarioLogeado());
        
        if(user == null){
            cadena = "No se ha podido actualizar la contraseña, no se ha encontrado ningun dato del usuario.";
        }else{
            if(!user.getPass().equals("{noop}"+actual)){
                cadena = "No se ha podido actualizar la contraseña, ya que la contraseña no coincide con el registro del sistema.";
            }else{
                 user.setPass("{noop}"+nueva); // Cambiar clave
                 usuarioService.Guardar(user);
                cadena ="OK";
            }
        }
        
        return cadena;
    }

    public String UsuarioLogeado() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        String username = userDetail.getUsername().trim();

        return username;
    }
}
