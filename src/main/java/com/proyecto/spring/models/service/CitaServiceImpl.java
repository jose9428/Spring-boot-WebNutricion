package com.proyecto.spring.models.service;

import com.proyecto.spring.models.entity.Cita;
import com.proyecto.spring.models.repository.CitaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CitaServiceImpl implements ICitaService {

    @Autowired
    private CitaRepository citaRepository;

    @Override
    public List<Cita> getList() {
        return citaRepository.findAll();
    }

    @Override
    public void ReservarCita(Cita c) {
        citaRepository.save(c);
    }

}
