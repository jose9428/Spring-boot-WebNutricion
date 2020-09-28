package com.proyecto.spring.models.service;

import com.proyecto.spring.models.entity.HorarioNutricionista;
import com.proyecto.spring.models.entity.Nutricionista;
import java.util.List;

public interface IHorarioNutricionistaService {
    
    public List<HorarioNutricionista> getAll();
    
    public List<Nutricionista> ListadoNutricionistaPorTurno(Long idTurno);
    
    public List ListadoNutricionistaDispTurno(Long idTurno);
}
