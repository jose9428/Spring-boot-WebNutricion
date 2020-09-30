package com.proyecto.spring.models.service;

import com.proyecto.spring.models.entity.*;
import com.proyecto.spring.models.repository.*;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdministradorServiceImpl implements IAdministradorService{
    
    @Autowired
    private AdministradorRepository administradorRepository;

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
    
}
