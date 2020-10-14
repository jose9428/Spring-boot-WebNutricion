package com.proyecto.spring.models.service;

import com.proyecto.spring.models.entity.*;
import java.util.List;

public interface IAdministradorService {

    public List<Administrador> getAll();

    public Administrador getById(Long id);

    public void Guardar(Administrador a);
    
    public String UsuarioGenerado();
    
      public List<Administrador> getListDisponibles();
}
