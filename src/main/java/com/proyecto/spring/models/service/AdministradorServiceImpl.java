package com.proyecto.spring.models.service;

import com.proyecto.spring.models.entity.*;
import com.proyecto.spring.models.repository.*;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdministradorServiceImpl implements IAdministradorService {

    @Autowired
    private AdministradorRepository administradorRepository;

    @Autowired
    private INutricionistaService nutricionistaService;

    @Autowired
    private IPacienteService pacienteService;

    @Override
    public List<Administrador> getAll() {
        return administradorRepository.findAll();
    }

    @Override
    public Administrador getById(Long id) {
        return administradorRepository.findById(id).orElse(null);
    }

    @Override
    public void Guardar(Administrador a) {
        administradorRepository.save(a);
    }

    @Override
    public String UsuarioGenerado() {
        return administradorRepository.UsuarioGenerado();
    }

    @Override
    public List<Administrador> getListDisponibles() {
        return administradorRepository.ListadoAdministradoresDisponibles();
    }

    @Override
    public List<Administrador> getListCorreo(String correo) {
        return administradorRepository.findByCorreo(correo);
    }

    @Override
    public boolean ExisteCorreo(String correo) {
        List<Administrador> admin = administradorRepository.findByCorreo(correo);
        List<Paciente> paciente = pacienteService.getListCorreo(correo);
        List<Nutricionista> medico = nutricionistaService.getListCorreo(correo);

        if (admin.size() > 0 || paciente.size() > 0 || medico.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

}
