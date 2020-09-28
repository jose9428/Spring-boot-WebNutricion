package com.proyecto.spring.models.service;

import com.proyecto.spring.models.entity.Usuario;
import java.util.List;
import org.springframework.stereotype.Service;
import com.proyecto.spring.models.repository.*;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UsuarioServiceImpl implements IUsuarioService{

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Override
    public List<Usuario> getAll() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario getById(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }
    
}
