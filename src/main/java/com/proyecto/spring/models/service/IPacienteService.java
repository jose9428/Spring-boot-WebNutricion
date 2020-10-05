package com.proyecto.spring.models.service;

import com.proyecto.spring.models.entity.*;
import java.util.List;

public interface IPacienteService {

    public List<Paciente> getAll();

    public Paciente getById(Long id);

    public void Guardar(Paciente p);

}
