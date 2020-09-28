package com.proyecto.spring.models.service;

import com.proyecto.spring.models.entity.Contextura;
import java.util.List;

public interface IContexturaService {

    public void GuardarContextura(Contextura c);

    public List<Contextura> getAll();

    public Contextura BuscarPorId(Long id);

    public int CantidadPorNombre(String nombre);

    public int CantidadPorNombreYId(String nombre, Long id);
}
