package com.proyecto.spring.models.service;

import com.proyecto.spring.models.entity.*;
import java.util.List;

public interface IPacienteService {

    public List<Paciente> getAll();

    public Paciente getById(Long id);

    public void Guardar(Paciente p);

    public int getCantDisponibles();

    public List<Paciente> getListCorreo(String correo);

    public Paciente ObtenerPorUsuario(String user);

    public boolean ExisteCorreo(String correo);
    
    public Paciente ObtenerDatosxCorreo(String correo);
    
      public Paciente ObtenerPorIdPaciente(Long codigo);
}
