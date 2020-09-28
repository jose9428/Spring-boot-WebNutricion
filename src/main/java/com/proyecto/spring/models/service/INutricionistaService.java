package com.proyecto.spring.models.service;

import com.proyecto.spring.models.entity.*;
import java.util.List;

public interface INutricionistaService {
    
        public List<Nutricionista> getAll();
        
        public Nutricionista getById(Long id);
        
        public String UsuarioGenerado();
        
        public void Guardar(Nutricionista n);
        
        public Long ObtenerIdUsuario(Long id);
        
        public void ActualizarEstado(Long id , int estado);
}
