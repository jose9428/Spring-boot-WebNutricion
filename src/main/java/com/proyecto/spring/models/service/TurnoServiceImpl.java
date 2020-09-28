package com.proyecto.spring.models.service;

import com.proyecto.spring.models.entity.Turno;
import com.proyecto.spring.models.repository.TurnoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TurnoServiceImpl implements ITurnoService{
    
    @Autowired
    private TurnoRepository turnoRepository;

    @Override
    public List<Turno> getAll() {
        return turnoRepository.findAll();
    }
    
}
