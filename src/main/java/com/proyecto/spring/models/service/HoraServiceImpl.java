package com.proyecto.spring.models.service;

import com.proyecto.spring.models.entity.Hora;
import com.proyecto.spring.models.repository.HoraRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HoraServiceImpl implements IHoraService {

    @Autowired
    private HoraRepository horaRepository;

    @Override
    public List<Hora> getAll() {
        return horaRepository.findAll();
    }

    @Override
    public List<Hora> HorariosDisp(Long idTurno, Long idMedico, String fecha) {
        List<Hora> lista;

        try {
            lista = horaRepository.ListadoHorariosDisp(idTurno, idMedico, fecha);
        } catch (Exception ex) {
            lista = new ArrayList<>();
        }

        return lista;
    }

    @Override
    public Hora getById(Long idHora) {
        return horaRepository.getById(idHora);
    }

}
