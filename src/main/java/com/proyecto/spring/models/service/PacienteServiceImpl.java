package com.proyecto.spring.models.service;

import com.proyecto.spring.models.entity.Administrador;
import com.proyecto.spring.models.entity.Nutricionista;
import com.proyecto.spring.models.entity.Paciente;
import com.proyecto.spring.models.repository.AdministradorRepository;
import com.proyecto.spring.models.repository.PacienteRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PacienteServiceImpl implements IPacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private IAdministradorService administradorService;

    @Autowired
    private INutricionistaService nutricionistaService;

    @Override
    public List<Paciente> getAll() {
        return pacienteRepository.findAll();
    }

    @Override
    public Paciente getById(Long id) {
        return pacienteRepository.findById(id).orElse(null);
    }

    @Override
    public void Guardar(Paciente p) {
        pacienteRepository.save(p);
    }

    @Override
    public int getCantDisponibles() {
        return pacienteRepository.CantPacientesDisponibles();
    }

    @Override
    public List<Paciente> getListCorreo(String correo) {
        return pacienteRepository.findByCorreo(correo);
    }

    @Override
    public Paciente ObtenerPorUsuario(String user) {
        return pacienteRepository.ObtenerPorUsuario(user);
    }

    @Override
    public boolean ExisteCorreo(String correo) {
        List<Administrador> admin = administradorService.getListCorreo(correo);
        List<Paciente> paciente = pacienteRepository.findByCorreo(correo);
        List<Nutricionista> medico = nutricionistaService.getListCorreo(correo);

        if (admin.size() > 0 || paciente.size() > 0 || medico.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Paciente ObtenerDatosxCorreo(String correo) {
        List<Paciente> lista = pacienteRepository.findByCorreo(correo);

        if (lista.size() > 0) {
            return lista.get(0);
        } else {
            return null;
        }

    }

}
