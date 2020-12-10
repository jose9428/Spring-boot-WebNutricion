package com.proyecto.spring.models.service;

import com.proyecto.spring.models.entity.*;
import java.util.List;

public interface ISeguimientoService {

    public List<Seguimiento> getAll();

    public void Guardar(Seguimiento s);
    
    public Seguimiento getById(Long idCita);
}
