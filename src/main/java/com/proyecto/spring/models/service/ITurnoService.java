package com.proyecto.spring.models.service;

import com.proyecto.spring.models.entity.Turno;
import java.util.List;
import org.springframework.stereotype.Service;

public interface ITurnoService {

    public List<Turno> getAll();

    public Turno getById(Long id);
}
