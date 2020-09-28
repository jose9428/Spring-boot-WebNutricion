package com.proyecto.spring.models.service;

import com.proyecto.spring.models.entity.HorarioNutricionista;
import com.proyecto.spring.models.entity.Nutricionista;
import com.proyecto.spring.models.repository.HorarioNutricionistaRepository;
import com.proyecto.spring.models.repository.NutricionistaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class HorarioNutricionistaServiceImpl implements IHorarioNutricionistaService {

    @Autowired
    private HorarioNutricionistaRepository nutricionistaRepository;

    @Override
    public List<HorarioNutricionista> getAll() {
        return nutricionistaRepository.findAll();
    }

    @Override
    public List<Nutricionista> ListadoNutricionistaPorTurno(Long idTurno) {
        return nutricionistaRepository.ListadoNutricionistaPorTurno(idTurno);
    }

    @Override
    public List ListadoNutricionistaDispTurno(Long idTurno) {
        return nutricionistaRepository.ListadoNutricionistaDispTurno(idTurno);
    }

}
