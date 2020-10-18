package com.proyecto.spring;

import com.fasterxml.jackson.databind.deser.std.StringArrayDeserializer;
import com.proyecto.spring.models.entity.Hora;
import com.proyecto.spring.models.entity.HorarioNutricionista;
import com.proyecto.spring.models.entity.Nutricionista;
import com.proyecto.spring.models.entity.Perfil;
import com.proyecto.spring.models.entity.Usuario;
import com.proyecto.spring.models.repository.ContexturaRepository;
import com.proyecto.spring.models.service.IContexturaService;
import com.proyecto.spring.models.service.IHoraService;
import com.proyecto.spring.models.service.IHorarioNutricionistaService;
import com.proyecto.spring.models.service.INutricionistaService;
import com.proyecto.spring.models.service.IPacienteService;
import com.proyecto.spring.models.service.IPerfilService;
import com.proyecto.spring.models.service.ITurnoService;
import com.proyecto.spring.models.service.IUsuarioService;
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

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        //log.warn("Prueba..!!");
        // log.warn(contexturaService.getAll().toString());
        //   List<Perfil> lista = perfilService.getAll();

        /*
        List lista = horaService.HorariosDisp(2L, 1L, "2020-10-18");

        for (int i = 0; i < lista.size(); i++) {
            Object[] o = (Object[]) lista.get(i);
            log.warn("" + o[0]+" \t"+o[1]+"\t"+o[2]);
        }
        */

    }

}
