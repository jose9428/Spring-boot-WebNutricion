package com.proyecto.spring.models.service;

import com.proyecto.spring.models.entity.Turno;
import java.util.List;
import org.springframework.stereotype.Service;

public interface ITurnoService {
    
    List<Turno> getAll();
    
    Turno getById(Long id);
}
