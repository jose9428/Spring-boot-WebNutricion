package com.proyecto.spring.models.service;

import com.proyecto.spring.models.entity.*;
import java.util.List;

public interface IUsuarioService {
        public List<Usuario> getAll();
        
        public Usuario getById(Long id);
}
