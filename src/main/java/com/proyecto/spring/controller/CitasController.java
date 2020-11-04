package com.proyecto.spring.controller;

import com.proyecto.spring.models.entity.Cita;
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
                c.setEstado("Pendiente");
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
}
