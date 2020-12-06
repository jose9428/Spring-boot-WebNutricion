package com.proyecto.spring.models.service;

import com.proyecto.spring.models.entity.Antropometrico;
import com.proyecto.spring.models.repository.AntropometricoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AntropometricoServiceImpl implements IAntropometricoService {

    @Autowired
    private AntropometricoRepository antropRepository;

    @Override
    public List<Antropometrico> getAll() {
        return antropRepository.findAll();
    }

    @Override
    public void Guardar(Antropometrico a) {
        antropRepository.save(a);
    }

    @Override
    public List<Antropometrico> ListadoPorPaciente(Long idPaciente) {
        return antropRepository.ListarPorPaciente(idPaciente);
    }

}
