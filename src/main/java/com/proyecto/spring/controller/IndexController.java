package com.proyecto.spring.controller;

import com.proyecto.spring.models.entity.*;
import com.proyecto.spring.models.service.*;
import com.proyecto.spring.util.Utileria;
import freemarker.core.ParseException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.annotations.Parameter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private INutricionistaService nutricionistaService;

    @Autowired
    private IAdministradorService adminService;

    @Autowired
    private IPacienteService pacienteService;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Configuration freemarkerConfig;

    @Value("${server.port}")
    String servidor;

    @GetMapping("/")
    public String Index() {
        return "/views/index";
    }

    @GetMapping("/login")
    public String IniciarSesion() {
        return "/views/Login";
    }

    @GetMapping("/acceso")
    public String Acceso() {
        return "redirect:/admin/";
    }

    @GetMapping("/servicios")
    public String Servicios(Model model) {
        return "/views/Servicios";
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

    //Nombre del usuario logeado
    public String NomUsuario() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails loggedUser = null;
        Object roles = null;
        if (principal instanceof UserDetails) {
            loggedUser = (UserDetails) principal;

        }

        return loggedUser.getUsername();
    }

    @GetMapping("/recuperar")
    public String Cambio() {

        return "/views/EnvioCorreoCambio";
    }

    @GetMapping("/validarToken")
    @ResponseBody
    public String ValidarToken(@RequestParam String token) {
        String mensaje = "";

        Usuario user = usuarioService.getByToken(token);

        if (user != null) {
            if (usuarioService.ValidarFechas(user.getId_Usuario())) {
                mensaje = "OK";
            } else {
                mensaje = "El código de recuperacion de contraseña ha expirado.Por favor intente denuevo.";
            }
        } else {
            mensaje = "El código de recuperacion de contraseña no es valido.Por favor intente denuevo.";
        }

        return mensaje;
    }

    @GetMapping("/reestablecer-clave")
    @ResponseBody
    public String CambiarContraseñaUsuario(@RequestParam String token, @RequestParam String contrasenia) {
        String mensaje = "";
        Usuario user = usuarioService.getByToken(token);

        if (!usuarioService.ValidarFechas(user.getId_Usuario())) {
            mensaje = "El código de recuperacion de contraseña ha expirado.Por favor intente denuevo.";
        } else if (user.getToken().equals(token)) {
            user.setPass("{noop}" + contrasenia);
            user.setToken(null);
            usuarioService.Guardar(user);
            mensaje = "OK";
        } else {
            mensaje = "No se ha podido cambiar la contraseña.Por favor pongase en contacto con un personal administrativo.";
        }

        return mensaje;
    }

    @GetMapping("/login/newPassword")
    public String NuevoPass(@RequestParam(required = false) String token, Model model) {
        if (token != null) {
            model.addAttribute("token", token);
            return "/views/CambiarContraseña";
        }
        return "redirect:/recuperar";
    }

    @GetMapping("/enviarToken")
    @ResponseBody
    public boolean PlantillaRecuperar(@RequestParam String correo) {
        boolean envio = true;
        List<Nutricionista> listaN = nutricionistaService.getListCorreo(correo);
        Usuario user = null;
        String token = Utileria.CodigoToken();

        if (listaN.size() > 0) {
            for (Nutricionista n : listaN) {
                envio = EnviarCorreo(n.getCorreo(), n.getNombres() + " , " + n.getApellido_Paterno(), token);
                user = n.getUsuario();
            }
        } else {
            List<Administrador> listaAdmin = adminService.getListCorreo(correo);

            if (listaAdmin.size() > 0) {
                for (Administrador a : listaAdmin) {
                    envio = EnviarCorreo(a.getCorreo(), a.getNombres() + " , " + a.getApellido_Paterno(), token);
                    user = a.getUsuario();
                }

            } else {
                List<Paciente> listaPaciente = pacienteService.getListCorreo(correo);

                if (listaPaciente.size() > 0) {
                    for (Paciente p : listaPaciente) {
                        envio = EnviarCorreo(p.getCorreo(), p.getNombres() + " , " + p.getApellido_Paterno(), token);
                        user = p.getUsuario();
                    }
                } else {
                    envio = false;
                }
            }
        }

        if (user != null) {
            user.setToken(token);
            user.setFecha_Recuperacion(new Date());
            usuarioService.Guardar(user);
        }

        return envio;
    }

    @Transactional
    public boolean EnviarCorreo(String correo, String nombre, String token) {
        boolean estado = false;

        try {
            Calendar c = Calendar.getInstance();
            Map<String, Object> model = new HashMap<>();
            model.put("anio", c.get(Calendar.YEAR));
            model.put("nombres", nombre);
            model.put("servidor", servidor);
            model.put("ruta", "http://localhost:" + servidor + "/login/newPassword?token=" + token);

            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED);

            Template t = freemarkerConfig.getTemplate("Plantilla-Recuperar-Clave.html");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

            SimpleMailMessage email = new SimpleMailMessage();

            helper.setTo(correo);
            helper.setText(html, true);
            helper.setSubject("Cambio de contraseña");

            mailSender.send(message);

            estado = true;
        } catch (Exception ex) {

        }

        return estado;
    }

    public byte[] getObtenerFoto() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        String username = userDetail.getUsername();

        Administrador a = adminService.ObtenerPorUsuario(username);

        if (a.getFoto() != null) {
            return a.getFoto();
        } else {
            Nutricionista n = nutricionistaService.ObtenerPorUsuario(username);
            if (n.getFoto() != null) {
                return n.getFoto();
            } else {

                Paciente p = pacienteService.ObtenerPorUsuario(username);
                if (p.getFoto() != null) {
                    return p.getFoto();
                }
            }
        }

        return null;
    }

    @GetMapping("/verImagenLogeado")
    @ResponseBody
    public void showImage(HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
        byte[] foto = getObtenerFoto();

        if (foto != null) {
            response.getOutputStream().write(foto);
        }

        response.getOutputStream().close();
    }

}
