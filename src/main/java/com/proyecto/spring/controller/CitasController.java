package com.proyecto.spring.controller;

import com.proyecto.spring.models.entity.Cita;
import com.proyecto.spring.models.entity.Hora;
import com.proyecto.spring.models.entity.Nutricionista;
import com.proyecto.spring.models.entity.Paciente;
import com.proyecto.spring.models.entity.Turno;
import com.proyecto.spring.models.entity.Usuario;
import com.proyecto.spring.models.service.ICitaService;
import com.proyecto.spring.models.service.IHoraService;
import com.proyecto.spring.models.service.IHorarioNutricionistaService;
import com.proyecto.spring.models.service.INutricionistaService;
import com.proyecto.spring.models.service.IPacienteService;
import com.proyecto.spring.models.service.ITurnoService;
import com.proyecto.spring.models.service.IUsuarioService;
import com.proyecto.spring.util.Utileria;
import java.util.Date;
import java.util.List;
import javax.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/citas")
public class CitasController {

    @Autowired
    private ITurnoService turnoService;

    @Autowired
    private IHorarioNutricionistaService horarioService;

    @Autowired
    private INutricionistaService nutricionistaService;

    @Autowired
    private IHoraService horaService;

    @Autowired
    private ICitaService citaService;

    @Autowired
    private IPacienteService pacienteService;
    
    @GetMapping("/DietaNutricional/{id}")
    public String ViewDietaNutricional(@PathVariable("id") Long idCita , Model model){
        model.addAttribute("idCita", idCita);
        return "/views/DietaNutricional";
    }

    @GetMapping("/Pendientes-Paciente")
    public String CitasPendientesMedico(Model model) {
        model.addAttribute("turnos", turnoService.getAll());
        return "/views/NutricionistaCitasPendientes";
    }

    @GetMapping(value = "/listarPendientes-Paciente")
    public String ListarCitasNutricionista(@RequestParam("fecha") String fecha, Model model) {

        Date fechaConv = Utileria.ConvertirFecha(fecha);
        List<Cita> listaCitas = citaService.ListarCitasPendientesPorNutricionista(fechaConv, UsuarioLogeado());

        model.addAttribute("fecha", fecha);
        model.addAttribute("listaCitas", listaCitas);
        return "/views/listar/ListarNutricionistaCitasPendientes";
    }

    @GetMapping("/medicos")
    public String CitasMedicos(Model model) {

        model.addAttribute("turnos", turnoService.getAll());

        return "/views/CitasMedicos";
    }

    @GetMapping("/pendientes")
    public String CitasPendientesPaciente(Model model) {

        return "/views/PacienteCitasPendientes";
    }

    @GetMapping(value = "/listarPendientesPaciente")
    public String ListarCitas(@RequestParam("fecha") String fecha, Model model) {

        Date fechaConv = Utileria.ConvertirFecha(fecha);
        List<Cita> listaCitas = citaService.ListarCitasPendientesPorPaciente(fechaConv, UsuarioLogeado());

        model.addAttribute("fecha", fecha);
        model.addAttribute("listaCitas", listaCitas);
        return "/views/listar/ListarPacienteCitasPendientes";
    }

    @GetMapping(value = "/listarHorario")
    public String ListarCitas(@RequestParam("idTurno") Long idTurno, Model model) {
        model.addAttribute("listaMedicos", horarioService.ListadoNutricionistaPorTurno(idTurno));
        model.addAttribute("turno", idTurno);
        return "/views/listar/ListarCitasNutricionista";
    }

    @GetMapping(value = "/listarMedicosTurno")
    @ResponseBody
    public List<Nutricionista> ListarMedicosPorTurno(@RequestParam("idTurno") Long idTurno) {
        List<Nutricionista> lista = horarioService.ListadoNutricionistaPorTurno(idTurno);
        return lista;
    }

    @GetMapping("/horarios/{idMedico}/{idTurno}")
    public String HorariosCitas(@PathVariable Long idMedico, @PathVariable Long idTurno, Model model) {
        Nutricionista n = nutricionistaService.getById(idMedico);
        model.addAttribute("medico", n);
        model.addAttribute("turno", turnoService.getById(idTurno));
        return "/views/CitasHorariosDisponibles";
    }

    @GetMapping("/listarCitasHorarios")
    public String ListarHorarios(@RequestParam Long idTurno,
            @RequestParam Long idMedico, @RequestParam String fecha, Model model) {

        model.addAttribute("horas", horaService.HorariosDisp(idTurno, idMedico, fecha));
        return "/views/listar/ListarCitasHorarios";
    }

    @PostMapping("/generarCita")
    public ResponseEntity<String> Generar(Cita c, String fecha) {

        try {
            String nomUsuario = UsuarioLogeado();
            Paciente paciente = pacienteService.ObtenerPorUsuario(nomUsuario);

            if (paciente != null) {
                c.setPaciente(paciente);
                c.setFecha_registro(new Date());
                c.setFecha_cita(Utileria.ConvertirFecha(fecha));
                c.setEstado("PENDIENTE");
                citaService.ReservarCita(c);

                return ResponseEntity.ok("OK");
            } else {
                return ResponseEntity.ok("No se ha podido generar la cita");
            }

        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("A ocurrido un error al momento de procesar la info : " + ex.getMessage()); // 400

        }

    }

    public String UsuarioLogeado() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        String username = userDetail.getUsername().trim();

        return username;
    }

    @GetMapping("/CitaHoy")
    public String MiCitaHoy(Model model) {

        List<Cita> listaCitas = citaService.ListarCitasPendientesPorPaciente(new Date(), UsuarioLogeado());
        model.addAttribute("listaCitas", listaCitas);
        return "/views/PacienteMiCitaHoy";
    }

    @GetMapping(value = "/atender/{id}")
    public String ViewAtenderCitas(Model model, @PathVariable("id") Long id) {

        if (id > 0) {
            model.addAttribute("cita", citaService.CitaDetalle(id, "Pendiente"));
            model.addAttribute("idCita", id);
            return "/views/AtenderCita";
        }

        return "redirect:/citas/Pendientes-Paciente";
    }

    @GetMapping(value = "/actividad/{id}")
    public String ViewNivelActividad(Model model, @PathVariable("id") Long id) {
        Cita c = citaService.CitaDetalle(id, "Pendiente");

        if (c != null) {
            model.addAttribute("idCita", id);
            model.addAttribute("paciente", c.getPaciente());
            return "/views/NivelActividad";
        }
        return "redirect:/citas/Pendientes-Paciente";
    }

    @GetMapping("/modificarCita")
    @ResponseBody
    public Cita DetalleCita(@RequestParam("idCita") Long idCita) {
        Cita d = citaService.CitaDetalle(idCita, "Pendiente");

        return d;
    }

    @GetMapping(value = "/listarHorariosDispon")
    @ResponseBody
    public List<Hora> ListarHorarios(@RequestParam("idTurno") Long idTurno,
            @RequestParam("fecha") String fecha, @RequestParam("idMedico") Long idMedico) {
        List<Hora> lista = horaService.HorariosDisp(idTurno, idMedico, fecha);
        return lista;
    }

    @GetMapping(value = "/modificar-datos")
    @ResponseBody
    public String ModificarCita(Cita cita , @RequestParam("fecha") String fecha) {

        try {
            cita.setFecha_cita(Utileria.ConvertirFecha(fecha));
            cita.setFecha_registro(new Date());
            cita.setEstado("Pendiente");
            citaService.ReservarCita(cita);
            
            return "OK";
        } catch (Exception ex) {
            return "No se ha podido modificar la cita : "+ex.getMessage();
        }
    }
}
