package com.proyecto.spring.models.service;

import com.proyecto.spring.models.entity.*;
import com.proyecto.spring.models.repository.NutricionistaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class NutricionistaServiceImpl implements INutricionistaService {

    @Autowired
    private NutricionistaRepository nutricionistaRepository;

    @Autowired
    private IAdministradorService administradorService;

    @Autowired
    private IPacienteService pacienteService;

    @Override
    public List<Nutricionista> getAll() {
        return nutricionistaRepository.findAll();
    }

    @Override
    public Nutricionista getById(Long id) {
        return nutricionistaRepository.findById(id).orElse(null);
    }

    @Override
    public String UsuarioGenerado() {
        return nutricionistaRepository.UsuarioGenerado();
    }

    @Override
    public void Guardar(Nutricionista n) {
        nutricionistaRepository.save(n);
    }

    @Override
    public Long ObtenerIdUsuario(Long id) {
        return nutricionistaRepository.ObtenerIdUsuario(id);
    }

    @Override
    public void ActualizarEstado(Long id, int estado) {
        Nutricionista n = getById(id);

        if (n != null) {
            Usuario u = n.getUsuario();
            u.setEstado(estado);
            n.setUsuario(u);
        }

        Guardar(n);
    }

    @Override
    public int getCantDisponibles() {
        return nutricionistaRepository.CantNutricionistaDisponibles();
    }

    @Override
    public Page<Nutricionista> getListDisponibles(Pageable pageable) {
        return nutricionistaRepository.ListadoNutricionistaDisponibles(pageable);
    }

    @Override
    public List<Nutricionista> getListCorreo(String correo) {
        return nutricionistaRepository.findByCorreo(correo);
    }

    @Override
    public boolean ExisteCorreo(String correo) {
        List<Administrador> admin = administradorService.getListCorreo(correo);
        List<Paciente> paciente = pacienteService.getListCorreo(correo);
        List<Nutricionista> medico = nutricionistaRepository.findByCorreo(correo);

        if (admin.size() > 0 || paciente.size() > 0 || medico.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean ExisteCorreoNotId(String correo, Long id) {
        List<Administrador> admin = administradorService.getListCorreo(correo);
        List<Paciente> paciente = pacienteService.getListCorreo(correo);
        List<Nutricionista> medico = nutricionistaRepository.findByCorreoAndId(correo, id);

        if (admin.size() > 0 || paciente.size() > 0 || medico.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

}
