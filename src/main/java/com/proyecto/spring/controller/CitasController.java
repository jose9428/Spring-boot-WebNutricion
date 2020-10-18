package com.proyecto.spring.controller;

import com.proyecto.spring.models.entity.Nutricionista;
import com.proyecto.spring.models.entity.Turno;
import com.proyecto.spring.models.service.IHoraService;
import com.proyecto.spring.models.service.IHorarioNutricionistaService;
import com.proyecto.spring.models.service.INutricionistaService;
import com.proyecto.spring.models.service.ITurnoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
 
    @GetMapping("/medicos")
    public String CitasMedicos(Model model) {

        model.addAttribute("turnos", turnoService.getAll());

        return "/views/CitasMedicos";
    }

    @GetMapping(value = "/listarHorario")
    public String ListarCitas(@RequestParam("idTurno") Long idTurno, Model model) {
        model.addAttribute("listaMedicos", horarioService.ListadoNutricionistaPorTurno(idTurno));
        model.addAttribute("turno", idTurno);
        return "/views/listar/ListarCitasNutricionista";
    }
    
    @GetMapping("/horarios/{idMedico}/{idTurno}")
    public String HorariosCitas(@PathVariable Long idMedico , @PathVariable Long idTurno,Model model){
        Nutricionista n = nutricionistaService.getById(idMedico);
        model.addAttribute("medico", n);
        model.addAttribute("turno", turnoService.getById(idTurno));
        return "/views/CitasHorariosDisponibles";
    }
    
    @GetMapping("/listarCitasHorarios")
    public String ListarHorarios(@RequestParam Long idTurno , 
            @RequestParam Long idMedico , @RequestParam String fecha, Model model){
        
        model.addAttribute("horas", horaService.HorariosDisp(idTurno, idMedico, fecha));
        return "/views/listar/ListarCitasHorarios";
    }
}
