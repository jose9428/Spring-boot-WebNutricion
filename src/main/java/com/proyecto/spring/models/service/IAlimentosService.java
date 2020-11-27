package com.proyecto.spring.models.service;

import com.proyecto.spring.models.entity.*;
import java.util.List;

public interface IAlimentosService {

    public void GuardarAlimentos(Alimentos a);

    public List<Alimentos> getAll();

    public Alimentos BuscarPorId(Long id);

    public int CantidadPorNombre(String nombre);

    public int CantidadPorNombreYId(String nombre , Long id);
}
