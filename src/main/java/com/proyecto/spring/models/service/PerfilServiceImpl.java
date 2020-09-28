package com.proyecto.spring.models.service;

import com.proyecto.spring.models.entity.Perfil;
import com.proyecto.spring.models.repository.PerfilRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PerfilServiceImpl implements IPerfilService{

    @Autowired
    private PerfilRepository perfilRepository;
    
    @Override
    public List<Perfil> getAll() {
        return perfilRepository.findAll();
    }
    
}
