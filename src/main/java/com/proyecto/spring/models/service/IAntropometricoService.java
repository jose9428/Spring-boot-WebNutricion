package com.proyecto.spring.models.service;

import com.proyecto.spring.models.entity.*;
import java.util.List;

public interface IAntropometricoService {

    public List<Antropometrico> getAll();
    
    public void Guardar(Antropometrico a);
}
