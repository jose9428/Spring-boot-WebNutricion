package com.proyecto.spring.models.service;

import com.proyecto.spring.models.entity.Usuario;
import java.util.List;
import org.springframework.stereotype.Service;
import com.proyecto.spring.models.repository.*;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

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

    @Override
    public void Guardar(Usuario user) {
        usuarioRepository.save(user);
    }

    @Override
    public Usuario getByToken(String token) {
        return usuarioRepository.findByToken(token);
    }

    @Override
    public boolean ValidarFechas(Long idUsuario) {
        long tiempo = usuarioRepository.DiferenciaFechasCambio(idUsuario);

        if (tiempo >= 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean ExisteUsuario(String user) {
        Usuario u = usuarioRepository.findByUsername(user);
        if (u != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Usuario getByUsuario(String user) {
        return usuarioRepository.findByUsername(user);
    }

}
