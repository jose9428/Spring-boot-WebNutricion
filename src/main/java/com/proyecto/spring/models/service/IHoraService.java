package com.proyecto.spring.models.service;

import com.proyecto.spring.models.entity.Hora;
import java.util.List;

public interface IHoraService {

    public List<Hora> getAll();

    public List<Hora> HorariosDisp(Long idTurno, Long idMedico, String fecha);

    public Hora getById(Long idHora);
}
