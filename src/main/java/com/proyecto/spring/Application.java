package com.proyecto.spring;

import com.fasterxml.jackson.databind.deser.std.StringArrayDeserializer;
import com.proyecto.spring.models.entity.Administrador;
import com.proyecto.spring.models.entity.Antropometrico;
import com.proyecto.spring.models.entity.Cita;
import com.proyecto.spring.models.entity.Hora;
import com.proyecto.spring.models.entity.HorarioNutricionista;
import com.proyecto.spring.models.entity.Nutricionista;
import com.proyecto.spring.models.entity.Paciente;
import com.proyecto.spring.models.entity.Perfil;
import com.proyecto.spring.models.entity.Reporte;
import com.proyecto.spring.models.entity.Usuario;
import com.proyecto.spring.models.repository.ContexturaRepository;
import com.proyecto.spring.models.service.IAdministradorService;
import com.proyecto.spring.models.service.IAntropometricoService;
import com.proyecto.spring.models.service.ICitaService;
import com.proyecto.spring.models.service.IContexturaService;
import com.proyecto.spring.models.service.IHoraService;
import com.proyecto.spring.models.service.IHorarioNutricionistaService;
import com.proyecto.spring.models.service.INutricionistaService;
import com.proyecto.spring.models.service.IPacienteService;
import com.proyecto.spring.models.service.IPerfilService;
import com.proyecto.spring.models.service.ITurnoService;
import com.proyecto.spring.models.service.IUsuarioService;
import com.proyecto.spring.util.Utileria;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class Application implements CommandLineRunner {

    @Autowired
    private IPerfilService perfilService;

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private INutricionistaService nutricionistaService;

    @Autowired
    private ITurnoService turnoService;

    @Autowired
    private IHoraService horaService;

    @Autowired
    private IHorarioNutricionistaService horarioService;

    @Autowired
    private IPacienteService pacienteService;

    @Autowired
    private IAdministradorService adminService;

    @Autowired
    private ICitaService citaService;

    @Autowired
    private IAntropometricoService antropService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
      
        log.info("Edad " + Utileria.CalcularEdad(new Date()));
    }

}
