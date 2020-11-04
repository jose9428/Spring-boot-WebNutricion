package com.proyecto.spring.models.service;

import com.proyecto.spring.models.entity.*;
import java.util.List;

public interface IAdministradorService {

    public List<Administrador> getAll();

    public Administrador getById(Long id);

    public void Guardar(Administrador a);
    
    public String UsuarioGenerado();
    
    public List<Administrador> getListDisponibles();
      
    public List<Administrador> getListCorreo(String correo);
    
    public boolean ExisteCorreo(String correo);
    
    public Administrador ObtenerPorUsuario(String user);
    
    public List<Reporte> ReportexEstados(String estado);
}
